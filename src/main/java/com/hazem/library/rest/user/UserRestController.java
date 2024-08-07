package com.hazem.library.rest.user;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazem.library.entity.User;
import com.hazem.library.payload.request.LoginRequest;
import com.hazem.library.payload.request.SignUpRequest;
import com.hazem.library.payload.response.JwtResponse;
import com.hazem.library.rest.exceptionHandler.InternalServerErrorException;
import com.hazem.library.rest.exceptionHandler.NotAuthorizedException;
import com.hazem.library.rest.exceptionHandler.NotFoundException;
import com.hazem.library.security.jwt.JwtUtils;
import com.hazem.library.service.user.UserService;

@RestController
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @Autowired
    JwtUtils jwtUtils;
    
    @Value("${library.app.saltRounds}")
    private int rounds;

    public String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt("$2b", rounds));
    }

    public boolean verifyHash(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }

    @GetMapping("")
    public List<User> index() {
        List<User> theUsers = userService.index();
        if (theUsers.isEmpty())
            throw new NotFoundException("No users found");
        return theUsers;
    }

    @PostMapping("sign_in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        User theUser = userService.getByName(loginRequest.getUser_name());
        if (verifyHash(loginRequest.getPassword(), theUser.getPassword())) {
            String jwt = jwtUtils.generateJwtToken(theUser);
            return ResponseEntity.ok(new JwtResponse(jwt, theUser.getId(), theUser.getUserName()));
        } else
            throw new NotAuthorizedException("Wrong user name or password");
    }

    @PostMapping("sign_up")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        // Create new user's account
        User user = new User(signUpRequest.getFirst_name(),
                signUpRequest.getLast_name(),
                signUpRequest.getUser_name(),
                hash(signUpRequest.getPassword()));
        User newUser = userService.saveUser(user);
        String jwt = jwtUtils.generateJwtToken(newUser);
        return ResponseEntity.ok(new JwtResponse(jwt, newUser.getId(), newUser.getUserName()));
    }

    @GetMapping("{userId}")
    public User getUser(@PathVariable Long userId, Principal principal) {
        User theUser = userService.getUser(userId);
        if (theUser == null) {
            throw new NotFoundException("User id not found - " + userId);
        }
        if (!(theUser.getUserName().equals(principal.getName()))) {
            throw new NotAuthorizedException("Unauthorized");
        }
        return theUser;
    }

    @PutMapping("{userId}")
    public User updatUser(@RequestBody User theUser, @PathVariable Long userId, Principal principal) {
        User tempUser = userService.getUser(userId);
        // throw exception if null
        if (tempUser == null)
            throw new NotFoundException("User id not found - " + userId);
        // validate user
        if (!(tempUser.getUserName().equals(principal.getName()))) {
            throw new NotAuthorizedException("Unauthorized");
        }
        // update user
        theUser.setId(userId);
        try {
            return userService.updateUser(theUser);
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not update user");
        }
    }

    // add mapping Delete /users/{userId} - delete existing user
    @DeleteMapping("{userId}")
    public String deleteUser(@PathVariable Long userId, Principal principal) {
        User tempUser = userService.getUser(userId);
        // throw exception if null
        if (tempUser == null)
            throw new NotFoundException("User id not found - " + userId);
        // validate user
        if (!(tempUser.getUserName().equals(principal.getName()))) {
            throw new NotAuthorizedException("Unauthorized");
        }
        try {
            userService.deleteUser(userId);
            return "Deleted User id - " + userId;
        } catch (Exception e) {
            throw new InternalServerErrorException("Could not delete user");
        }
    }
}
