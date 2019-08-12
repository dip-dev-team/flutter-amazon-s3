# amazon_s3_cognito

Amazon S3 plugin for Flutter

Unofficial Amazon S3 plugin written in Dart for Flutter.

The plugin is extension if flutter-amazon-s3 plugin which can be found here 
https://pub.dev/packages/flutter_amazon_s3. This plugin adds image delete functionality and also
it allows user to upload image when region and sub-region are different.

Plugin in maintained by fäm properties<no-reply@famproperties.com>.

## Usage
To use this plugin, add `amazon_s3_cognito` as a [dependency in your pubspec.yaml file](https://flutter.io/platform-plugins/).


```yaml
dependencies:
  amazon_s3_cognito: '^0.0.9'
```

### Example


``` dart
import 'package:amazon_s3_cognito/flutter_amazon_s3.dart';
import 'package:amazon_s3_cognito/aws_region.dart';

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
IOS and Android Plugins are modified by fäm properties to enable image upload and delete using region and sub-region. 
Android version written by Tony Darko
IOS version written by Vladislav Blago
```
