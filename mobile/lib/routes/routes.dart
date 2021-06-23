import 'package:ecosearch/routes/router_handler.dart';
import 'package:fluro/fluro.dart';

class Routes {
  static const home = "/";
  static const login = "/login";
  static const register = "/register";

  static void configureRouter(FluroRouter router) {
    router.define(
      home,
      handler: homeHandler,
      transitionType: TransitionType.inFromBottom,
    );
    router.define(
      login,
      handler: loginHandler,
      transitionType: TransitionType.inFromBottom,
    );
    router.define(
      register,
      handler: registerHandler,
      transitionType: TransitionType.inFromBottom,
    );
  }
}
