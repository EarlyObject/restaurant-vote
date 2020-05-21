package com.earlyobject.ws.ui.controller;

import com.earlyobject.ws.ui.model.request.LoginRequestModel;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @ApiOperation("User login")
    @ApiImplicitParams({@ApiImplicitParam(name = "login",
            value = "${authenticationController.login.description}",
            paramType = "header")})
    @ApiResponses(value = {
            @ApiResponse(code = 200,
                    message = "Response Headers",
                    responseHeaders = {
                            @ResponseHeader(name = "authorization",
                                    description = "Bearer <JWT value here>", response = String.class),
                            @ResponseHeader(name = "userId",
                                    description = "<Public User Id value here>", response = String.class)
                    })
    })
    @PostMapping("/users/login")
    public void theFakeLogin(@RequestBody LoginRequestModel loginRequestModel){
        throw new IllegalStateException("This method should not be called. This method is implemented by Spring " +
                "Security");
    }
}
