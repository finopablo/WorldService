package edu.utn.countries.WorldService.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.utn.countries.WorldService.domain.User;
import edu.utn.countries.WorldService.dto.LoginRequestDto;
import edu.utn.countries.WorldService.dto.LoginResponseDto;
import edu.utn.countries.WorldService.dto.UserDto;
import edu.utn.countries.WorldService.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static edu.utn.countries.WorldService.util.Constants.JWT_SECRET;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class UserController {

        private final UserService userService;
        private final ObjectMapper objectMapper;
        private final ModelMapper modelMapper;

    @Autowired
        public UserController(UserService userService, ObjectMapper objectMapper, ModelMapper modelMapper) {
            this.userService = userService;
            this.objectMapper = objectMapper;
            this.modelMapper = modelMapper;
        }


        @PostMapping(value = "login")
        public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
                log.info(loginRequestDto.toString());
                User user = userService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());
                if (user!=null){
                            UserDto dto = modelMapper.map(user, UserDto.class);
                            return ResponseEntity.ok(LoginResponseDto.builder().token(this.generateToken(dto)).build());
                } else {
                            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
        }


    @GetMapping(value = "/userDetails")
    public ResponseEntity<User> userDetails(Authentication auth) {
            return ResponseEntity.ok((User) auth.getPrincipal());
    }


    private String generateToken(UserDto userDto) {
           try {
               List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("DEFAULT_USER");
               String token = Jwts
                        .builder()
                        .setId("JWT")
                        .setSubject(userDto.getUsername())
                        .claim("user", objectMapper.writeValueAsString(userDto))
                        .claim("authorities",grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000000))
                        .signWith(SignatureAlgorithm.HS512, JWT_SECRET.getBytes()).compact();
                return  token;
            } catch(Exception e) {
                return "dummy";
            }



        }
}
