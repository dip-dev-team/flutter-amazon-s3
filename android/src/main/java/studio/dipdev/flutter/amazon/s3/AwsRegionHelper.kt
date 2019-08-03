package studio.dipdev.flutter.amazon.s3

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.Region
import java.io.File
import java.io.UnsupportedEncodingException
import java.util.Locale


class AwsRegionHelper(private val context: Context, private val onUploadCompleteListener: OnUploadCompleteListener,
                      private val BUCKET_NAME: String, private val IDENTITY_POOL_ID: String,
                      private val IMAGE_NAME: String,private val REGION: String, private val SUB_REGION: String) {

    private var transferUtility: TransferUtility
    private var nameOfUploadedFile: String? = null
    private var region1:Regions = Regions.DEFAULT_REGION
    private var subRegion1:Regions = Regions.DEFAULT_REGION


    init {

        initRegion()
        val credentialsProvider = CognitoCachingCredentialsProvider(context, IDENTITY_POOL_ID, region1)
        val amazonS3Client = AmazonS3Client(credentialsProvider)
        amazonS3Client.setRegion(com.amazonaws.regions.Region.getRegion(subRegion1))

        transferUtility = TransferUtility(amazonS3Client, context)
    }

    private val uploadedUrl: String
        get() = getUploadedUrl(nameOfUploadedFile)

    private fun getUploadedUrl(key: String?): String {
        return "https://"+region1.getName()+"amazonaws.com/"+BUCKET_NAME+"/"+key
        //return  ""+key
    }

    private fun initRegion(){

        region1 = getRegionFor(REGION);
        subRegion1 = getRegionFor(SUB_REGION);

    }

    @Throws(UnsupportedEncodingException::class)
    fun deleteImage(): String {

        initRegion()

        val credentialsProvider = CognitoCachingCredentialsProvider(context, IDENTITY_POOL_ID, region1)

        val amazonS3Client = AmazonS3Client(credentialsProvider)
        amazonS3Client.setRegion(com.amazonaws.regions.Region.getRegion(subRegion1))
        Thread(Runnable{
            amazonS3Client.deleteObject(BUCKET_NAME, IMAGE_NAME)
            onUploadCompleteListener.onUploadComplete("Success")
        }).start()
        return IMAGE_NAME

    }

    @Throws(UnsupportedEncodingException::class)
    fun uploadImage(image: File): String {

        initRegion()

        val credentialsProvider = CognitoCachingCredentialsProvider(context, IDENTITY_POOL_ID, region1)

        val amazonS3Client = AmazonS3Client(credentialsProvider)
        amazonS3Client.setRegion(com.amazonaws.regions.Region.getRegion(subRegion1))
        transferUtility = TransferUtility(amazonS3Client, context)

        nameOfUploadedFile = IMAGE_NAME;

        val transferObserver = transferUtility.upload(BUCKET_NAME, nameOfUploadedFile, image)

        transferObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    onUploadCompleteListener.onUploadComplete(getUploadedUrl(nameOfUploadedFile))
                }
                if (state == TransferState.FAILED) {
                    onUploadCompleteListener.onFailed()
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {}
            override fun onError(id: Int, ex: Exception) {
                onUploadCompleteListener.onFailed()
                Log.e(TAG, "error in upload id [ " + id + " ] : " + ex.message)

            }
        })
        return uploadedUrl
    }

    @Throws(UnsupportedEncodingException::class)
    fun clean(filePath: String): String {
        return filePath.replace("[^.A-Za-z0-9]".toRegex(), "")
    }

    interface OnUploadCompleteListener {
        fun onUploadComplete(imageUrl: String)
        fun onFailed()
    }

    companion object {
        private val TAG = AwsRegionHelper::class.java.simpleName
        private const val URL_TEMPLATE = "https://s3.amazonaws.com/%s/%s"
    }

    private fun  getRegionFor(name:String):Regions{

        if(name == "US_EAST_1"){
            return Regions.US_EAST_1
        }else if(name == "AP_SOUTHEAST_1"){
            return Regions.AP_SOUTHEAST_1
        }else if(name == "US_EAST_2"){
            return Regions.US_EAST_2
        }else if(name == "EU_WEST_1"){
            return Regions.EU_WEST_1
        }else if(name == "CA_CENTRAL_1"){
            return Regions.CA_CENTRAL_1
        }else if(name == "CN_NORTH_1"){
            return Regions.CN_NORTH_1
        } else if(name == "CN_NORTHWEST_1"){
            return Regions.CN_NORTHWEST_1
        }else if(name == "EU_CENTRAL_1"){
            return Regions.EU_CENTRAL_1
        } else if(name == "EU_WEST_2"){
            return Regions.EU_WEST_2
        }else if(name == "EU_WEST_3"){
            return Regions.EU_WEST_3
        } else if(name == "SA_EAST_1"){
            return Regions.SA_EAST_1
        } else if(name == "US_WEST_1"){
            return Regions.US_WEST_1
        }else if(name == "US_WEST_2"){
            return Regions.US_WEST_2
        } else if(name == "AP_NORTHEAST_1"){
            return Regions.AP_NORTHEAST_1
        } else if(name == "AP_NORTHEAST_2"){
            return Regions.AP_NORTHEAST_2
        } else if(name == "AP_SOUTHEAST_1"){
            return Regions.AP_SOUTHEAST_1
        }else if(name == "AP_SOUTHEAST_2"){
            return Regions.AP_SOUTHEAST_2
        } else if(name == "AP_SOUTH_1"){
            return Regions.GovCloud
        }else if(name == "AP_SOUTH_1"){
            return Regions.GovCloud
        }

        return Regions.DEFAULT_REGION

    }


}package studio.dipdev.flutter.amazon.s3

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

import com.amazonaws.auth.CognitoCachingCredentialsProvider
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.Region
import java.io.File
import java.io.UnsupportedEncodingException
import java.util.Locale


class AwsRegionHelper(private val context: Context, private val onUploadCompleteListener: OnUploadCompleteListener,
                      private val BUCKET_NAME: String, private val IDENTITY_POOL_ID: String,
                      private val IMAGE_NAME: String,private val REGION: String, private val SUB_REGION: String) {

    private var transferUtility: TransferUtility
    private var nameOfUploadedFile: String? = null
    private var region1:Regions = Regions.DEFAULT_REGION
    private var subRegion1:Regions = Regions.DEFAULT_REGION


    init {

        initRegion()
        val credentialsProvider = CognitoCachingCredentialsProvider(context, IDENTITY_POOL_ID, region1)
        val amazonS3Client = AmazonS3Client(credentialsProvider)
        amazonS3Client.setRegion(com.amazonaws.regions.Region.getRegion(subRegion1))

        transferUtility = TransferUtility(amazonS3Client, context)
    }

    private val uploadedUrl: String
        get() = getUploadedUrl(nameOfUploadedFile)

    private fun getUploadedUrl(key: String?): String {
        return "https://"+region1.getName()+".amazonaws.com/"+BUCKET_NAME+"/"+key
        //return  ""+key
    }

    private fun initRegion(){

        region1 = getRegionFor(REGION);
        subRegion1 = getRegionFor(SUB_REGION);

    }

    @Throws(UnsupportedEncodingException::class)
    fun deleteImage(): String {

        initRegion()

        val credentialsProvider = CognitoCachingCredentialsProvider(context, IDENTITY_POOL_ID, region1)

        val amazonS3Client = AmazonS3Client(credentialsProvider)
        amazonS3Client.setRegion(com.amazonaws.regions.Region.getRegion(subRegion1))
        Thread(Runnable{
            amazonS3Client.deleteObject(BUCKET_NAME, IMAGE_NAME)
            onUploadCompleteListener.onUploadComplete("Success")
        }).start()
        return IMAGE_NAME

    }

    @Throws(UnsupportedEncodingException::class)
    fun uploadImage(image: File): String {

        initRegion()

        val credentialsProvider = CognitoCachingCredentialsProvider(context, IDENTITY_POOL_ID, region1)

        val amazonS3Client = AmazonS3Client(credentialsProvider)
        amazonS3Client.setRegion(com.amazonaws.regions.Region.getRegion(subRegion1))
        transferUtility = TransferUtility(amazonS3Client, context)

        nameOfUploadedFile = IMAGE_NAME;

        val transferObserver = transferUtility.upload(BUCKET_NAME, nameOfUploadedFile, image)

        transferObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    onUploadCompleteListener.onUploadComplete(getUploadedUrl(nameOfUploadedFile))
                }
                if (state == TransferState.FAILED) {
                    onUploadCompleteListener.onFailed()
                }
            }

            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {}
            override fun onError(id: Int, ex: Exception) {
                onUploadCompleteListener.onFailed()
                Log.e(TAG, "error in upload id [ " + id + " ] : " + ex.message)

            }
        })
        return uploadedUrl
    }

    @Throws(UnsupportedEncodingException::class)
    fun clean(filePath: String): String {
        return filePath.replace("[^.A-Za-z0-9]".toRegex(), "")
    }

    interface OnUploadCompleteListener {
        fun onUploadComplete(imageUrl: String)
        fun onFailed()
    }

    companion object {
        private val TAG = AwsRegionHelper::class.java.simpleName
        private const val URL_TEMPLATE = "https://s3.amazonaws.com/%s/%s"
    }

    private fun  getRegionFor(name:String):Regions{

        if(name == "US_EAST_1"){
            return Regions.US_EAST_1
        }else if(name == "AP_SOUTHEAST_1"){
            return Regions.AP_SOUTHEAST_1
        }else if(name == "US_EAST_2"){
            return Regions.US_EAST_2
        }else if(name == "EU_WEST_1"){
            return Regions.EU_WEST_1
        }else if(name == "CA_CENTRAL_1"){
            return Regions.CA_CENTRAL_1
        }else if(name == "CN_NORTH_1"){
            return Regions.CN_NORTH_1
        } else if(name == "CN_NORTHWEST_1"){
            return Regions.CN_NORTHWEST_1
        }else if(name == "EU_CENTRAL_1"){
            return Regions.EU_CENTRAL_1
        } else if(name == "EU_WEST_2"){
            return Regions.EU_WEST_2
        }else if(name == "EU_WEST_3"){
            return Regions.EU_WEST_3
        } else if(name == "SA_EAST_1"){
            return Regions.SA_EAST_1
        } else if(name == "US_WEST_1"){
            return Regions.US_WEST_1
        }else if(name == "US_WEST_2"){
            return Regions.US_WEST_2
        } else if(name == "AP_NORTHEAST_1"){
            return Regions.AP_NORTHEAST_1
        } else if(name == "AP_NORTHEAST_2"){
            return Regions.AP_NORTHEAST_2
        } else if(name == "AP_SOUTHEAST_1"){
            return Regions.AP_SOUTHEAST_1
        }else if(name == "AP_SOUTHEAST_2"){
            return Regions.AP_SOUTHEAST_2
        } else if(name == "AP_SOUTH_1"){
            return Regions.GovCloud
        }else if(name == "AP_SOUTH_1"){
            return Regions.GovCloud
        }

        return Regions.DEFAULT_REGION

    }


}