import 'package:ecosearch/auth.dart';
import 'package:flutter/material.dart';

void main() => runApp(EcoSearch());

class EcoSearch extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'EcoSearch',
      theme: ThemeData(
        primaryColor: Color(0xff54B0F3),
        accentColor: Color(0xffB1C5C4),
      ),
    );
  }
}
