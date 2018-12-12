import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter_amazon_s3/flutter_amazon_s3.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  Future<void> initPlatformState() async {
    //TODO ------->
    await FlutterAmazonS3.uploadImage('Will image url');
    if (!mounted) return;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Image.network(
            'https://i1.rozetka.ua/goods/5184757/tp_link_tl_wr841n_images_5184757752.jpg',
            height: 200.0,
            width: 200.0,
          ),
        ),
      ),
    );
  }
}
