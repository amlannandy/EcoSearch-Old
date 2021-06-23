import 'package:ecosearch/screens/home/home_screen.dart';
import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';

import 'package:ecosearch/screens/login/login_screen.dart';
import 'package:ecosearch/screens/register/register_screen.dart';

var homeHandler = Handler(
    handlerFunc: (BuildContext? context, Map<String, List<String>> params) {
  return HomeScreen();
});

var loginHandler = Handler(
    handlerFunc: (BuildContext? context, Map<String, List<String>> params) {
  return LoginScreen();
});

var registerHandler = Handler(
    handlerFunc: (BuildContext? context, Map<String, List<String>> params) {
  return RegisterScreen();
});
