# flutter_amazon_s3

Amazon S3 plugin for Flutter

Unofficial Amazon S3 plugin written in Dart for Flutter.

## Usage
To use this plugin, add `flutter_amazon_s3` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).


```yaml
dependencies:
  flutter_amazon_s3: '^0.0.9'
```

### Example


``` dart
import 'package:flutter_amazon_s3/flutter_amazon_s3.dart';
import 'package:flutter_amazon_s3/aws_region.dart';

String uploadedImageUrl = await FlutterAmazonS3.uploadImage(
          _image.path, BUCKET_NAME, IDENTITY_POOL_ID);
          

//Use the below code to specify the region and sub region for image upload
String uploadedImageUrl = await FlutterAmazonS3.upload(
            _image.path,
            BUCKET_NAME,
            IDENTITY_POOL_ID,
            IMAGE_NAME,
            AwsRegion.US_EAST_1,
            AwsRegion.AP_SOUTHEAST_1)
            
//use below code to delete an image
 String result = FlutterAmazonS3.delete(
            BUCKET_NAME,
            IDENTITY_POOL_ID,
            IMAGE_NAME,
            AwsRegion.US_EAST_1,
            AwsRegion.AP_SOUTHEAST_1)
            
            
        

```
          
## Installation


### Android

No configuration required - the plugin should work out of the box.          


### iOS

No configuration required - the plugin should work out of the box.          

### Authors
```
Android version written by Tony Darko
IOS version written by Vladislav Blago
IOS and Android Plugins are modified by fäm properties to enable image upload and delete using region and sub-region
```
