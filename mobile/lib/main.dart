import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'package:ecosearch/auth.dart';
import 'package:ecosearch/routes/routes.dart';
import 'package:ecosearch/routes/application.dart';

void main() => runApp(EcoSearch());

class EcoSearch extends StatelessWidget {
  EcoSearch() {
    final router = FluroRouter();
    Routes.configureRouter(router);
    Application.router = router;
  }

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider<AuthState>(
      create: (context) => AuthState(),
      child: MaterialApp(
        title: 'EcoSearch',
        theme: ThemeData(
          primaryColor: Color(0xff54B0F3),
          accentColor: Color(0xffB1C5C4),
        ),
        debugShowCheckedModeBanner: false,
        onGenerateRoute: Application.router.generator,
      ),
    );
  }
}
