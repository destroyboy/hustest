package live.bolder.hustest;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.instagram.igdiskcache.EditorOutputStream;
import com.instagram.igdiskcache.SnapshotInputStream;
import com.instagram.igdiskcache.IgDiskCache;
import com.instagram.igdiskcache.OptionalStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


/**
 * uses Instagrams disk based cache, this is just a thin layer to do the initialiation
 * creates a thread for doing the IO and has a handler for doing the reading/writing
 * and another handler to post the result back to the UI thread.
 * Also has a function to turn any kind of string into a valid key for indexing the
 * cache entries.
 */

public class DiskCache {
    IgDiskCache mDiskCache;

    final long maxCacheSizeInBytes = 256 * 1024 * 256;

    Handler mCacheThreadHandler;
    Handler mUiThreadHandler = new Handler( Looper.getMainLooper( ) );
    HandlerThread cacheThread;

    interface DiskCacheLoaded {
        void onDiskCacheCreated(DiskCache diskCache );
    }

    interface Callback {
        void onResult( String key, String value );
    }

    interface SerializableCallback {
        void onResult( String key, Serializable value );
    }

    DiskCache( MainActivity activity, DiskCacheLoaded callback ) {
        cacheThread = new HandlerThread( "DiskCache" );
        cacheThread.start();
        mCacheThreadHandler = new Handler( cacheThread.getLooper( ) );
        mCacheThreadHandler.post( ( ) -> {
            mDiskCache = new IgDiskCache( activity.getCacheDir(), maxCacheSizeInBytes );
            if ( callback != null )
                mUiThreadHandler.post(( ) -> callback.onDiskCacheCreated( DiskCache.this ));
        });
    }

    static UUID UUIDfrom( String key ) {
        return UUID.nameUUIDFromBytes( key.getBytes() );
    }

    static byte[] byteArrayfromStream( InputStream stream ) {
        byte[] result = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] data = new byte[ 1024 ];
            for( ;; ) {
                int read = stream.read( data, 0, data.length );
                if ( read == -1 )
                    break;
                buffer.write( data, 0, read );
            }
            buffer.flush();
            result = buffer.toByteArray();
        }
        catch( Exception ignored ) {

        }
        return result;
    }

    private byte[] getData( UUID key ) {

        OptionalStream<SnapshotInputStream> inputStream = mDiskCache.get( key.toString() );
        if ( !inputStream.isPresent() )
            return null;

        byte[] data;
        try { data = byteArrayfromStream( inputStream.get( ) ); }
        finally {
            try { inputStream.get( ).close( ); }
            catch( Exception ignored ) { }
        }
        return data;
    }

    public void getString( String key, Callback callback ) {
        mCacheThreadHandler.post( ( ) -> {
            byte[] data = getData( UUIDfrom( key ) );
            String result = data == null ? null : new String( data, StandardCharsets.UTF_8);
            mUiThreadHandler.post( ( ) -> {
                callback.onResult( key, result );
            });
        });
    }

    private void putData( UUID key, byte[] data ) {
        OptionalStream<EditorOutputStream>  outputStream = mDiskCache.edit( key.toString() );
        if ( outputStream.isPresent( ) ) {
            try {
                outputStream.get().write( data );
                outputStream.get().commit( );
            } finally {
                outputStream.get().abortUnlessCommitted();
            }
        }
    }

    public void putString( String key, String value, Callback callback ) {
        mCacheThreadHandler.post( ( ) -> {
            putData( UUIDfrom( key ), value.getBytes() );
            if ( callback != null )
                mUiThreadHandler.post( ( ) -> {
                    callback.onResult( key, value );
                });
        });
    }

    void putSerializable( String key, Serializable value, SerializableCallback callback )  {
        /// FIXME: maybe don't need to create intermediate byte array??
        /// FIXME: maybe better to do the serialization on the handler thread???

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject( value );
            oos.flush();
            byte [] data = bos.toByteArray();
            mCacheThreadHandler.post( ( ) -> {
                putData( UUIDfrom( key ), data );
                if ( callback != null )
                    mUiThreadHandler.post( ( ) -> {
                        callback.onResult( key, value );
                    });
            });
        }
        catch ( IOException e ) {
            callback.onResult( key, null );
        }
    }

    public void getSerializable( String key, SerializableCallback callback ) {
        mCacheThreadHandler.post( ( ) -> {
            Serializable result  = null;
            byte[] data = getData( UUIDfrom( key ) );
            if ( data != null ) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream( data );
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    result = (Serializable) ois.readObject();
                }
                catch ( IOException | ClassNotFoundException ignore ) {

                }
            }
            final Serializable fResult = result;
            //String result = data == null ? null : new String( data, StandardCharsets.UTF_8);
            mUiThreadHandler.post( ( ) -> {
                callback.onResult( key, fResult );
            });
        });
    }
}