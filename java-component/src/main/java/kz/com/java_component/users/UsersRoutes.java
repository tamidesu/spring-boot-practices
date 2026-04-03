//package kz.com.java_component.users;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.BodyExtractors;
//import org.springframework.web.reactive.function.server.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class UsersRoutes {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Bean
//    public RouterFunction<ServerResponse> usersRoute() {
//        return RouterFunctions.route(
//                RequestPredicates.GET("/users"),
//                request -> ServerResponse.ok()
//                        .body(userRepository.findAll(), User.class)
//        );
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> postUserRoute() {
//        return RouterFunctions.route(
//                RequestPredicates.POST("/users"),
//                request -> request
//                        .body(BodyExtractors.toMono(User.class))
//                        .flatMap(entity -> userRepository.save(entity))
//                        .then(ServerResponse.ok().build())
//        );
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> findUserByEmail() {
//        return RouterFunctions.route(
//                RequestPredicates.GET("/users/{email}"),
//                request -> ServerResponse.ok()
//                        .body(userRepository.findByEmail(
//                                request.pathVariable("email")), User.class)
//        );
//    }
//
//    @Bean
//    public RouterFunction<ServerResponse> deleteUserByEmail() {
//        return RouterFunctions.route(
//                RequestPredicates.DELETE("/users/{email}"),
//                request -> userRepository
//                        .deleteByEmail(request.pathVariable("email"))
//                        .then(ServerResponse.noContent().build())
//        );
//    }
//}
