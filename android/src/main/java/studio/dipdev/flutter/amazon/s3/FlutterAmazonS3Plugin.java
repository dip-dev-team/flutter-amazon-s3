package studio.dipdev.flutter.amazon.s3;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.UnsupportedEncodingException;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

public class FlutterAmazonS3Plugin implements MethodCallHandler, AwsHelper.OnUploadCompleteListener {
  AwsHelper awsHelper;
  Context context;
  public static void registerWith(PluginRegistry.Registrar registrar) {
    MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_amazon_s3");
    FlutterAmazonS3Plugin instance = new FlutterAmazonS3Plugin(registrar.context());
    channel.setMethodCallHandler(instance);
  }

  private FlutterAmazonS3Plugin(Context context) {
    this.context = context;
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    String filePath = call.argument("filePath");
    String bucket = call.argument("bucket");
    String identity = call.argument("identity");
    String uploadedUrl = "";
    if (call.method.equals("uploadImageToAmazon")) {
      File file = new File(filePath);
      try {
        awsHelper = new AwsHelper(context, this, bucket, identity);
        uploadedUrl = awsHelper.uploadImage(file);
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
      result.success(uploadedUrl);
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onUploadComplete(@NotNull String imageUrl) { }

  @Override
  public void onFailed() { }
}
