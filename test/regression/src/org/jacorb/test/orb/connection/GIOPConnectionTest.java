package org.jacorb.test.orb.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jacorb.config.Configuration;
import org.jacorb.config.JacORBConfiguration;
import org.jacorb.orb.ORB;
import org.jacorb.orb.giop.ClientConnection;
import org.jacorb.orb.giop.GIOPConnection;
import org.jacorb.orb.giop.GIOPConnectionManager;
import org.jacorb.orb.giop.LocateRequestOutputStream;
import org.jacorb.orb.giop.MessageOutputStream;
import org.jacorb.orb.giop.Messages;
import org.jacorb.orb.giop.ReplyInputStream;
import org.jacorb.orb.giop.ReplyListener;
import org.jacorb.orb.giop.RequestInputStream;
import org.jacorb.orb.giop.RequestListener;
import org.jacorb.orb.giop.RequestOutputStream;
import org.jacorb.orb.giop.ServerGIOPConnection;
import org.jacorb.orb.iiop.IIOPAddress;
import org.jacorb.orb.iiop.IIOPProfile;
import org.jacorb.test.common.JacORBTestCase;
import org.jacorb.test.common.JacORBTestSuite;
import org.omg.ETF.BufferHolder;
import org.omg.ETF.Profile;
import org.omg.GIOP.MsgType_1_1;

/**
 * GIOPConnectionTest.java
 *
 *
 * Created: Sat Jun 22 14:26:15 2002
 *
 * @jacorb-client-since 2.2
 * @author Nicolas Noffke
 */

public class GIOPConnectionTest
    extends JacORBTestCase
{
    private Configuration config;
    private ORB orb;

    public void setUp()
        throws Exception
    {
        java.util.Properties props = null;

        props = new java.util.Properties();
        props.setProperty("org.omg.CORBA.ORBSingletonClass","org.jacorb.orb.ORBSingleton");
        props.setProperty("org.omg.CORBA.ORBClass","org.jacorb.orb.ORB");


        orb = (ORB) ORB.init(new String[0], props);
        config = JacORBConfiguration.getConfiguration(null, orb, false);
    }

    protected void tearDown() throws Exception
    {
        config = null;
        orb.shutdown(true);
        orb = null;
    }

    public static Test suite()
    {
        TestSuite suite = new JacORBTestSuite ("GIOPConnection Test",
                                               GIOPConnectionTest.class);


        suite.addTest (new GIOPConnectionTest ("testGIOP_1_0_CorrectRefusing"));

        //GIOP 1.1
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_IllegalMessageType"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_CorrectRequest"));

        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_Boolean"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_BooleanArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_Char"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_CharArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_WChar"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_WCharArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_Octet"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_OctetArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_Short"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_ShortArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_uShort"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_uShortArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_Long"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_LongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_uLong"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_uLongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_LongLong"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_LongLongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_uLongLong"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_uLongLongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_Float"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_FloatArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_Double"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_DoubleArray"));

        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readString"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_1_readFragmented_String"));


        //GIOP 1.2
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_CorrectFragmentedRequest"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_CorrectCloseOnGarbage"));

        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_Boolean"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_BooleanArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_Char"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_CharArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_WChar"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_WCharArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_Octet"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_OctetArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_Short"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_ShortArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_uShort"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_uShortArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_Long"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_LongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_uLong"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_uLongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_LongLong"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_LongLongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_uLongLong"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_uLongLongArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_Float"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_FloatArray"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_Double"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_DoubleArray"));

        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readString"));
        suite.addTest (new GIOPConnectionTest ("testGIOP_1_2_readFragmented_String"));

        return suite;
    }


    class DummyTransport extends org.omg.ETF._ConnectionLocalBase
    {
        private boolean closed = false;
        private byte[] data = null;
        private int index = 0;
        private ByteArrayOutputStream b_out = new ByteArrayOutputStream();
        private org.omg.ETF.Profile profile = new IIOPProfile
        (
            new IIOPAddress ("127.0.0.1", 4711),
            null,
            orb.getGIOPMinorVersion()
        );

        public DummyTransport( List<byte[]> messages )
        {
            // convert the message list into a plain byte array

            int size = 0;
            for (Iterator<byte[]> i = messages.iterator(); i.hasNext();)
            {
                size += i.next().length;
            }
            data = new byte[size];
            int index = 0;
            for (Iterator<byte[]> i = messages.iterator(); i.hasNext();)
            {
                byte[] msg = i.next();
                System.arraycopy(msg, 0, data, index, msg.length);
                index += msg.length;
            }
        }

        public byte[] getWrittenMessage()
        {
            return b_out.toByteArray();
        }

        public void connect (org.omg.ETF.Profile profile, long time_out)
        {
            // nothing
        }

        public boolean hasBeenClosed()
        {
            return closed;
        }

        public boolean is_connected()
        {
            return !closed;
        }

        public void write( boolean is_first, boolean is_last,
                           byte[] message, int start, int size,
                           long timeout )
        {
            b_out.write( message, start, size );
        }


        public void flush()
        {
        }

        public void close()
        {
            closed = true;
        }

        public boolean isSSL()
        {
            return false;
        }

        public void turnOnFinalTimeout()
        {
        }

        public Profile get_server_profile()
        {
            return profile;
        }

        public int read (BufferHolder data, int offset,
                         int min_length, int max_length, long time_out)
        {
            if (this.index + min_length > this.data.length)
            {
                throw new org.omg.CORBA.COMM_FAILURE ("end of stream");
            }
            System.arraycopy(this.data, this.index, data.value, offset, min_length);
            this.index += min_length;
            return min_length;
        }

        public boolean is_data_available()
        {
            return true;
        }

        public boolean supports_callback()
        {
            return false;
        }

        public boolean use_handle_time_out()
        {
            return false;
        }

        public boolean wait_next_data(long time_out)
        {
            return false;
        }

    }


    private class DummyRequestListener
        implements RequestListener
    {
        private byte[] request = null;

        public DummyRequestListener()
        {
        }

        public byte[] getRequest()
        {
            return request;
        }

        public void requestReceived( byte[] request,
                                     GIOPConnection connection )
        {
            this.request = request;
        }

        public void locateRequestReceived( byte[] request,
                                           GIOPConnection connection )
        {
            this.request = request;
        }
        public void cancelRequestReceived( byte[] request,
                                           GIOPConnection connection )
        {
            this.request = request;
        }
    }

    private class DummyReplyListener
        implements ReplyListener
    {
        private byte[] reply = null;

        public DummyReplyListener()
        {
        }

        public byte[] getReply()
        {
            return reply;
        }

        public void replyReceived( byte[] reply,
                                   GIOPConnection connection )
        {
            this.reply = reply;
        }

        public void locateReplyReceived( byte[] reply,
                                         GIOPConnection connection )
        {
            this.reply = reply;
        }

        public void closeConnectionReceived( byte[] close_conn,
                                             GIOPConnection connection )
        {
            this.reply = close_conn;
        }

    }

    public GIOPConnectionTest( String name )
    {
        super( name );
    }

    public void testGIOP_1_1_readFragmented_String()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulong( 10 ); //string length
        r_out.write_octet( (byte) 'b' );
        r_out.write_octet( (byte) 'a' );
        r_out.write_octet( (byte) 'r' );
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_octet( (byte) 'b' );
        m_out.write_octet( (byte) 'a' );
        m_out.write_octet( (byte) 'z' );
        m_out.insertMsgSize();

        b = m_out.getBufferCopy();
        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        m_out = new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);

        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                1 // giop minor
              );
        m_out.write_octet( (byte) 'b' );
        m_out.write_octet( (byte) 'a' );
        m_out.write_octet( (byte) 'r' );
        m_out.write_octet( (byte) 0);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );


        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        String result = r_in.read_string();

        //is the body correct?
        assertEquals( "barbazbar", result);
    }

    public void testGIOP_1_2_readFragmented_String()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulong( 10 ); //string length
        r_out.write_octet( (byte) 'b' );
        r_out.write_octet( (byte) 'a' );
        r_out.write_octet( (byte) 'r' );
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_octet( (byte) 'b' );
        m_out.write_octet( (byte) 'a' );
        m_out.write_octet( (byte) 'z' );
        m_out.insertMsgSize();

        b = m_out.getBufferCopy();
        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        m_out = new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);

        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_octet( (byte) 'b' );
        m_out.write_octet( (byte) 'a' );
        m_out.write_octet( (byte) 'r' );
        m_out.write_octet( (byte) 0);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );


        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        String result = r_in.read_string();

        //is the body correct?
        assertEquals( "barbazbar", result);
    }


    public void testGIOP_1_1_readFragmented_BooleanArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        boolean[] data = new boolean[6];
        r_in.read_boolean_array(data, 0, 6);

        //is the data correct?
        assertEquals( false, data[0]);
        assertEquals( false, data[1]);
        assertEquals( false, data[2]);
        assertEquals( true, data[3]);
        assertEquals( true, data[4]);
        assertEquals( true, data[5]);
    }

    public void testGIOP_1_1_readFragmented_Boolean()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        boolean[] data = new boolean[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_boolean();

        //is the data correct?
        assertEquals( false, data[0]);
        assertEquals( false, data[1]);
        assertEquals( false, data[2]);
        assertEquals( true, data[3]);
        assertEquals( true, data[4]);
        assertEquals( true, data[5]);
    }

    public void testGIOP_1_2_readFragmented_Boolean()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong( 0 ); // Fragment Header (request id)
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        boolean[] data = new boolean[19];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_boolean();

        //is the data correct?
        assertEquals( false, data[0]);
        assertEquals( false, data[1]);
        assertEquals( false, data[2]);
        assertEquals( true, data[3]);
        assertEquals( true, data[4]);
        assertEquals( true, data[5]);
        assertEquals( true, data[6]);
        assertEquals( false, data[7]);
        assertEquals( false, data[8]);
        assertEquals( false, data[9]);
        assertEquals( false, data[10]);
        assertEquals( true, data[11]);
        assertEquals( true, data[12]);
        assertEquals( true, data[13]);
        assertEquals( true, data[14]);
        assertEquals( false, data[15]);
        assertEquals( false, data[16]);
        assertEquals( false, data[17]);
        assertEquals( false, data[18]);
    }

    public void testGIOP_1_2_readFragmented_Char()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        r_out.write_char('a');
        r_out.write_char('b');
        r_out.write_char('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong( 0 ); // Fragment Header (request id)
        m_out.write_char('d');
        m_out.write_char('e');
        m_out.write_char('f');
        m_out.write_char('g');
        m_out.write_char('h');
        m_out.write_char('i');
        m_out.write_char('j');
        m_out.write_char('k');
        m_out.write_char('l');
        m_out.write_char('m');
        m_out.write_char('n');
        m_out.write_char('o');
        m_out.write_char('p');
        m_out.write_char('q');
        m_out.write_char('r');
        m_out.write_char('s');
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[19];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_char();

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);
        assertEquals( 'f', data[5]);
        assertEquals( 'g', data[6]);
        assertEquals( 'h', data[7]);
        assertEquals( 'i', data[8]);
        assertEquals( 'j', data[9]);
        assertEquals( 'k', data[10]);
        assertEquals( 'l', data[11]);
        assertEquals( 'm', data[12]);
        assertEquals( 'n', data[13]);
        assertEquals( 'o', data[14]);
        assertEquals( 'p', data[15]);
        assertEquals( 'q', data[16]);
        assertEquals( 'r', data[17]);
        assertEquals( 's', data[18]);
    }

    public void testGIOP_1_2_readFragmented_CharArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        r_out.write_char('a');
        r_out.write_char('b');
        r_out.write_char('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong( 0 ); // Fragment Header (request id)
        m_out.write_char('d');
        m_out.write_char('e');
        m_out.write_char('f');
        m_out.write_char('g');
        m_out.write_char('h');
        m_out.write_char('i');
        m_out.write_char('j');
        m_out.write_char('k');
        m_out.write_char('l');
        m_out.write_char('m');
        m_out.write_char('n');
        m_out.write_char('o');
        m_out.write_char('p');
        m_out.write_char('q');
        m_out.write_char('r');
        m_out.write_char('s');
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[19];

        r_in.read_char_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);
        assertEquals( 'f', data[5]);
        assertEquals( 'g', data[6]);
        assertEquals( 'h', data[7]);
        assertEquals( 'i', data[8]);
        assertEquals( 'j', data[9]);
        assertEquals( 'k', data[10]);
        assertEquals( 'l', data[11]);
        assertEquals( 'm', data[12]);
        assertEquals( 'n', data[13]);
        assertEquals( 'o', data[14]);
        assertEquals( 'p', data[15]);
        assertEquals( 'q', data[16]);
        assertEquals( 'r', data[17]);
        assertEquals( 's', data[18]);
    }

    public void testGIOP_1_2_readFragmented_Double()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_double(1.1);
        r_out.write_double(1.2);
        r_out.write_double(1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_double(1.4);
        m_out.write_double(1.5);
        m_out.write_double(1.6);
        m_out.write_double(1.7);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        double[] data = new double[7];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_double();

        //is the data correct?
        assertEquals( 1.1 , data[0]);
        assertEquals( 1.2 , data[1]);
        assertEquals( 1.3 , data[2]);
        assertEquals( 1.4 , data[3]);
        assertEquals( 1.5 , data[4]);
        assertEquals( 1.6 , data[5]);
        assertEquals( 1.7 , data[6]);
    }

    public void testGIOP_1_2_readFragmented_DoubleArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_double(1.1);
        r_out.write_double(1.2);
        r_out.write_double(1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_double(1.4);
        m_out.write_double(1.5);
        m_out.write_double(1.6);
        m_out.write_double(1.7);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        double[] data = new double[7];

        r_in.read_double_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 1.1 , data[0]);
        assertEquals( 1.2 , data[1]);
        assertEquals( 1.3 , data[2]);
        assertEquals( 1.4 , data[3]);
        assertEquals( 1.5 , data[4]);
        assertEquals( 1.6 , data[5]);
        assertEquals( 1.7 , data[6]);
    }

    public void testGIOP_1_2_readFragmented_Float()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_float((float) 1.1);
        r_out.write_float((float) 1.2);
        r_out.write_float((float) 1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_float((float) 1.4);
        m_out.write_float((float) 1.5);
        m_out.write_float((float) 1.6);
        m_out.write_float((float) 1.7);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        float[] data = new float[7];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_float();

        //is the data correct?
        assertEquals( (float) 1.1 , data[0]);
        assertEquals( (float) 1.2 , data[1]);
        assertEquals( (float) 1.3 , data[2]);
        assertEquals( (float) 1.4 , data[3]);
        assertEquals( (float) 1.5 , data[4]);
        assertEquals( (float) 1.6 , data[5]);
        assertEquals( (float) 1.7 , data[6]);
    }

    public void testGIOP_1_2_readFragmented_FloatArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_float((float) 1.1);
        r_out.write_float((float) 1.2);
        r_out.write_float((float) 1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_float((float) 1.4);
        m_out.write_float((float) 1.5);
        m_out.write_float((float) 1.6);
        m_out.write_float((float) 1.7);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        float[] data = new float[7];

        r_in.read_float_array(data, 0, data.length);

        //is the data correct?
        assertEquals( (float) 1.1 , data[0]);
        assertEquals( (float) 1.2 , data[1]);
        assertEquals( (float) 1.3 , data[2]);
        assertEquals( (float) 1.4 , data[3]);
        assertEquals( (float) 1.5 , data[4]);
        assertEquals( (float) 1.6 , data[5]);
        assertEquals( (float) 1.7 , data[6]);
    }

    public void testGIOP_1_2_readFragmented_Long()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_long(0);
        r_out.write_long(1);
        r_out.write_long(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_long(3);
        m_out.write_long(4);
        m_out.write_long(5);
        m_out.write_long(6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[7];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_long();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
        assertEquals( 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_LongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_long(0);
        r_out.write_long(1);
        r_out.write_long(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_long(3);
        m_out.write_long(4);
        m_out.write_long(5);
        m_out.write_long(6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[7];

        r_in.read_long_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
        assertEquals( 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_LongLong()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_longlong(0);
        r_out.write_longlong(1);
        r_out.write_longlong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_longlong(3);
        m_out.write_longlong(4);
        m_out.write_longlong(5);
        m_out.write_longlong(6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[7];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_longlong();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
        assertEquals( 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_LongLongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_longlong(0);
        r_out.write_longlong(1);
        r_out.write_longlong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_longlong(3);
        m_out.write_longlong(4);
        m_out.write_longlong(5);
        m_out.write_longlong(6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[7];

        r_in.read_longlong_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
        assertEquals( 6, data[6]);
    }


    public void testGIOP_1_2_readFragmented_BooleanArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.write_boolean(false);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong( 0 ); // Fragment Header (request id)
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(true);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.write_boolean(false);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        boolean[] data = new boolean[19];

        r_in.read_boolean_array(data, 0, data.length);

        //is the data correct?
        assertEquals( false, data[0]);
        assertEquals( false, data[1]);
        assertEquals( false, data[2]);
        assertEquals( true, data[3]);
        assertEquals( true, data[4]);
        assertEquals( true, data[5]);
        assertEquals( true, data[6]);
        assertEquals( false, data[7]);
        assertEquals( false, data[8]);
        assertEquals( false, data[9]);
        assertEquals( false, data[10]);
        assertEquals( true, data[11]);
        assertEquals( true, data[12]);
        assertEquals( true, data[13]);
        assertEquals( true, data[14]);
        assertEquals( false, data[15]);
        assertEquals( false, data[16]);
        assertEquals( false, data[17]);
        assertEquals( false, data[18]);
    }


    public void testGIOP_1_1_readFragmented_OctetArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_octet( (byte) 0);
        r_out.write_octet( (byte) 1);
        r_out.write_octet( (byte) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_octet( (byte) 3);
        m_out.write_octet( (byte) 4);
        m_out.write_octet( (byte) 5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        byte[] data = new byte[6];
        r_in.read_octet_array(data, 0, 6);

        //is the data correct?
        assertEquals( (byte) 0, data[0]);
        assertEquals( (byte) 1, data[1]);
        assertEquals( (byte) 2, data[2]);
        assertEquals( (byte) 3, data[3]);
        assertEquals( (byte) 4, data[4]);
        assertEquals( (byte) 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_Octet()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_octet( (byte) 0);
        r_out.write_octet( (byte) 1);
        r_out.write_octet( (byte) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_octet( (byte) 3);
        m_out.write_octet( (byte) 4);
        m_out.write_octet( (byte) 5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        byte[] data = new byte[6];

        for(int i = 0; i < data.length; i++)
            data[i] = r_in.read_octet();

        //is the data correct?
        assertEquals( (byte) 0, data[0]);
        assertEquals( (byte) 1, data[1]);
        assertEquals( (byte) 2, data[2]);
        assertEquals( (byte) 3, data[3]);
        assertEquals( (byte) 4, data[4]);
        assertEquals( (byte) 5, data[5]);
    }

    public void testGIOP_1_2_readFragmented_Octet()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_octet( (byte) 0);
        r_out.write_octet( (byte) 1);
        r_out.write_octet( (byte) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_octet( (byte) 3);
        m_out.write_octet( (byte) 4);
        m_out.write_octet( (byte) 5);
        m_out.write_octet( (byte) 6);
        m_out.write_octet( (byte) 7);
        m_out.write_octet( (byte) 8);
        m_out.write_octet( (byte) 9);
        m_out.write_octet( (byte) 10);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        byte[] data = new byte[11];

        for(int i = 0; i < data.length; i++)
            data[i] = r_in.read_octet();

        //is the data correct?
        assertEquals( (byte) 0, data[0]);
        assertEquals( (byte) 1, data[1]);
        assertEquals( (byte) 2, data[2]);
        assertEquals( (byte) 3, data[3]);
        assertEquals( (byte) 4, data[4]);
        assertEquals( (byte) 5, data[5]);
        assertEquals( (byte) 6, data[6]);
        assertEquals( (byte) 7, data[7]);
        assertEquals( (byte) 8, data[8]);
        assertEquals( (byte) 9, data[9]);
        assertEquals( (byte) 10, data[10]);
    }

    public void testGIOP_1_2_readFragmented_OctetArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_octet( (byte) 0);
        r_out.write_octet( (byte) 1);
        r_out.write_octet( (byte) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_octet( (byte) 3);
        m_out.write_octet( (byte) 4);
        m_out.write_octet( (byte) 5);
        m_out.write_octet( (byte) 6);
        m_out.write_octet( (byte) 7);
        m_out.write_octet( (byte) 8);
        m_out.write_octet( (byte) 9);
        m_out.write_octet( (byte) 10);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        byte[] data = new byte[11];

        r_in.read_octet_array(data, 0, data.length);

        //is the data correct?
        assertEquals( (byte) 0, data[0]);
        assertEquals( (byte) 1, data[1]);
        assertEquals( (byte) 2, data[2]);
        assertEquals( (byte) 3, data[3]);
        assertEquals( (byte) 4, data[4]);
        assertEquals( (byte) 5, data[5]);
        assertEquals( (byte) 6, data[6]);
        assertEquals( (byte) 7, data[7]);
        assertEquals( (byte) 8, data[8]);
        assertEquals( (byte) 9, data[9]);
        assertEquals( (byte) 10, data[10]);
    }

    public void testGIOP_1_2_readFragmented_Short()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_short( (short) 0);
        r_out.write_short( (short) 1);
        r_out.write_short( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_short( (short) 3);
        m_out.write_short( (short) 4);
        m_out.write_short( (short) 5);
        m_out.write_short( (short) 6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[7];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_short();

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
        assertEquals( (short) 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_ShortArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_short( (short) 0);
        r_out.write_short( (short) 1);
        r_out.write_short( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_short( (short) 3);
        m_out.write_short( (short) 4);
        m_out.write_short( (short) 5);
        m_out.write_short( (short) 6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[7];

        r_in.read_short_array(data, 0, data.length);

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
        assertEquals( (short) 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_uLong()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulong(0);
        r_out.write_ulong(1);
        r_out.write_ulong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_ulong(3);
        m_out.write_ulong(4);
        m_out.write_ulong(5);
        m_out.write_ulong(6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[7];

        for(int i=0; i < data.length; i++)
            data[i]  = r_in.read_ulong();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
        assertEquals( 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_uLongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulong(0);
        r_out.write_ulong(1);
        r_out.write_ulong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_ulong(3);
        m_out.write_ulong(4);
        m_out.write_ulong(5);
        m_out.write_ulong(6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[7];

        r_in.read_ulong_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
        assertEquals( 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_uLongLong()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulonglong(0);
        r_out.write_ulonglong(1);
        r_out.write_ulonglong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_ulonglong(3);
        m_out.write_ulonglong(4);
        m_out.write_ulonglong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_ulonglong();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_2_readFragmented_uLongLongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulonglong(0);
        r_out.write_ulonglong(1);
        r_out.write_ulonglong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_ulonglong(3);
        m_out.write_ulonglong(4);
        m_out.write_ulonglong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[6];

        r_in.read_ulonglong_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_ShortArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_short( (short) 0);
        r_out.write_short( (short) 1);
        r_out.write_short( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_short( (short) 3);
        m_out.write_short( (short) 4);
        m_out.write_short( (short) 5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[6];
        r_in.read_short_array(data, 0, 6);

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_Short()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_short( (short) 0);
        r_out.write_short( (short) 1);
        r_out.write_short( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_short( (short) 3);
        m_out.write_short( (short) 4);
        m_out.write_short( (short) 5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_short();

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_uShortArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ushort( (short) 0);
        r_out.write_ushort( (short) 1);
        r_out.write_ushort( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_ushort( (short) 3);
        m_out.write_ushort( (short) 4);
        m_out.write_ushort( (short) 5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[6];
        r_in.read_ushort_array(data, 0, 6);

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_uShort()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ushort( (short) 0);
        r_out.write_ushort( (short) 1);
        r_out.write_ushort( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_ushort( (short) 3);
        m_out.write_ushort( (short) 4);
        m_out.write_ushort( (short) 5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_ushort();

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
    }

    public void testGIOP_1_2_readFragmented_uShort()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ushort( (short) 0);
        r_out.write_ushort( (short) 1);
        r_out.write_ushort( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_ushort( (short) 3);
        m_out.write_ushort( (short) 4);
        m_out.write_ushort( (short) 5);
        m_out.write_ushort( (short) 6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[7];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_ushort();

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
        assertEquals( (short) 6, data[6]);
    }
    public void testGIOP_1_2_readFragmented_uShortArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ushort( (short) 0);
        r_out.write_ushort( (short) 1);
        r_out.write_ushort( (short) 2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_ushort( (short) 3);
        m_out.write_ushort( (short) 4);
        m_out.write_ushort( (short) 5);
        m_out.write_ushort( (short) 6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        short[] data = new short[7];

        r_in.read_ushort_array(data, 0, data.length);

        //is the data correct?
        assertEquals( (short) 0, data[0]);
        assertEquals( (short) 1, data[1]);
        assertEquals( (short) 2, data[2]);
        assertEquals( (short) 3, data[3]);
        assertEquals( (short) 4, data[4]);
        assertEquals( (short) 5, data[5]);
        assertEquals( (short) 6, data[6]);
    }

    public void testGIOP_1_2_readFragmented_WChar()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_wchar('a');
        r_out.write_wchar('b');
        r_out.write_wchar('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_wchar('d');
        m_out.write_wchar('e');
        m_out.write_byte((byte) 0); //padding
        m_out.write_byte((byte) 0); //padding
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[5];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_wchar();

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);

    }

    public void testGIOP_1_2_readFragmented_WCharArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_wchar('a');
        r_out.write_wchar('b');
        r_out.write_wchar('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(2);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  2 // giop minor
                                );
        m_out.write_ulong(0);
        m_out.write_wchar('d');
        m_out.write_wchar('e');
        m_out.write_byte((byte) 0); //padding
        m_out.write_byte((byte) 0); //padding
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[5];

        r_in.read_wchar_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);

    }

    public void testGIOP_1_1_readFragmented_LongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_long(0);
        r_out.write_long(1);
        r_out.write_long(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_long(3);
        m_out.write_long(4);
        m_out.write_long(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[6];
        r_in.read_long_array(data, 0, 6);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_Long()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_long(0);
        r_out.write_long(1);
        r_out.write_long(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_long(3);
        m_out.write_long(4);
        m_out.write_long(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_long();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_uLongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulong(0);
        r_out.write_ulong(1);
        r_out.write_ulong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_ulong(3);
        m_out.write_ulong(4);
        m_out.write_ulong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[6];
        r_in.read_ulong_array(data, 0, 6);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_uLong()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulong(0);
        r_out.write_ulong(1);
        r_out.write_ulong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_ulong(3);
        m_out.write_ulong(4);
        m_out.write_ulong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        int[] data = new int[6];

        for(int i=0; i < data.length; i++)
            data[i]  = r_in.read_ulong();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_LongLongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_longlong(0);
        r_out.write_longlong(1);
        r_out.write_longlong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_longlong(3);
        m_out.write_longlong(4);
        m_out.write_longlong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[6];
        r_in.read_longlong_array(data, 0, 6);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_LongLong()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_longlong(0);
        r_out.write_longlong(1);
        r_out.write_longlong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_longlong(3);
        m_out.write_longlong(4);
        m_out.write_longlong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_longlong();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_uLongLongArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulonglong(0);
        r_out.write_ulonglong(1);
        r_out.write_ulonglong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_ulonglong(3);
        m_out.write_ulonglong(4);
        m_out.write_ulonglong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[6];
        r_in.read_ulonglong_array(data, 0, 6);

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_uLongLong()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_ulonglong(0);
        r_out.write_ulonglong(1);
        r_out.write_ulonglong(2);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_ulonglong(3);
        m_out.write_ulonglong(4);
        m_out.write_ulonglong(5);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        long[] data = new long[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_ulonglong();

        //is the data correct?
        assertEquals( 0, data[0]);
        assertEquals( 1, data[1]);
        assertEquals( 2, data[2]);
        assertEquals( 3, data[3]);
        assertEquals( 4, data[4]);
        assertEquals( 5, data[5]);
    }

    public void testGIOP_1_1_readFragmented_FloatArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_float((float) 1.1);
        r_out.write_float((float) 1.2);
        r_out.write_float((float) 1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_float((float) 1.4);
        m_out.write_float((float) 1.5);
        m_out.write_float((float) 1.6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        float[] data = new float[6];
        r_in.read_float_array(data, 0, 6);

        //is the data correct?
        assertEquals( (float) 1.1 , data[0]);
        assertEquals( (float) 1.2 , data[1]);
        assertEquals( (float) 1.3 , data[2]);
        assertEquals( (float) 1.4 , data[3]);
        assertEquals( (float) 1.5 , data[4]);
        assertEquals( (float) 1.6 , data[5]);
    }

    public void testGIOP_1_1_readFragmented_Float()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_float((float) 1.1);
        r_out.write_float((float) 1.2);
        r_out.write_float((float) 1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_float((float) 1.4);
        m_out.write_float((float) 1.5);
        m_out.write_float((float) 1.6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        float[] data = new float[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_float();

        //is the data correct?
        assertEquals( (float) 1.1 , data[0]);
        assertEquals( (float) 1.2 , data[1]);
        assertEquals( (float) 1.3 , data[2]);
        assertEquals( (float) 1.4 , data[3]);
        assertEquals( (float) 1.5 , data[4]);
        assertEquals( (float) 1.6 , data[5]);
    }

    public void testGIOP_1_1_readFragmented_DoubleArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_double(1.1);
        r_out.write_double(1.2);
        r_out.write_double(1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_double(1.4);
        m_out.write_double(1.5);
        m_out.write_double(1.6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        double[] data = new double[6];
        r_in.read_double_array(data, 0, 6);

        //is the data correct?
        assertEquals( 1.1 , data[0]);
        assertEquals( 1.2 , data[1]);
        assertEquals( 1.3 , data[2]);
        assertEquals( 1.4 , data[3]);
        assertEquals( 1.5 , data[4]);
        assertEquals( 1.6 , data[5]);
    }

    public void testGIOP_1_1_readFragmented_Double()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_double(1.1);
        r_out.write_double(1.2);
        r_out.write_double(1.3);
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_double(1.4);
        m_out.write_double(1.5);
        m_out.write_double(1.6);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        double[] data = new double[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_double();

        //is the data correct?
        assertEquals( 1.1 , data[0]);
        assertEquals( 1.2 , data[1]);
        assertEquals( 1.3 , data[2]);
        assertEquals( 1.4 , data[3]);
        assertEquals( 1.5 , data[4]);
        assertEquals( 1.6 , data[5]);
    }

    public void testGIOP_1_1_readFragmented_CharArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_char('a');
        r_out.write_char('b');
        r_out.write_char('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_char('d');
        m_out.write_char('e');
        m_out.write_char('f');
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[6];
        r_in.read_char_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);
        assertEquals( 'f', data[5]);
    }

    public void testGIOP_1_1_readFragmented_Char()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_char('a');
        r_out.write_char('b');
        r_out.write_char('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_char('d');
        m_out.write_char('e');
        m_out.write_char('f');
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_char();

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);
        assertEquals( 'f', data[5]);
    }

    public void testGIOP_1_1_readFragmented_WCharArray()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_wchar('a');
        r_out.write_wchar('b');
        r_out.write_wchar('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_wchar('d');
        m_out.write_wchar('e');
        m_out.write_wchar('f');
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[6];
        r_in.read_wchar_array(data, 0, data.length);

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);
        assertEquals( 'f', data[5]);
    }

    public void testGIOP_1_1_readFragmented_WChar()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_wchar('a');
        r_out.write_wchar('b');
        r_out.write_wchar('c');
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        //next fragment
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.setGIOPMinor(1);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                  1 // giop minor
                                );
        m_out.write_wchar('d');
        m_out.write_wchar('e');
        m_out.write_wchar('f');
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        char[] data = new char[6];

        for(int i=0; i < data.length; i++)
            data[i] = r_in.read_wchar();

        //is the data correct?
        assertEquals( 'a', data[0]);
        assertEquals( 'b', data[1]);
        assertEquals( 'c', data[2]);
        assertEquals( 'd', data[3]);
        assertEquals( 'e', data[4]);
        assertEquals( 'f', data[5]);
    }

    public void testGIOP_1_2_readString()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_long(5);
        r_out.write_octet( (byte) 'a');
        r_out.write_octet( (byte) 'b');
        r_out.write_octet( (byte) 'c');
        r_out.write_octet( (byte) 'd');
        r_out.write_octet( (byte) 0);

        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();


        messages.add( b );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        String result = r_in.read_string();

        //is the data correct?
        assertEquals( "abcd", result);
    }

    public void testGIOP_1_1_readString()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                   );

        //manually write the first half of the string "barbaz"
        r_out.write_long(5);
        r_out.write_octet( (byte) 'a');
        r_out.write_octet( (byte) 'b');
        r_out.write_octet( (byte) 'c');
        r_out.write_octet( (byte) 'd');
        r_out.write_octet( (byte) 0);

        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();


        messages.add( b );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                    transport,
                    request_listener,
                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
        ( orb, null, request_listener.getRequest() );

        String result = r_in.read_string();

        //is the data correct?
        assertEquals( "abcd", result);
    }

    public void testGIOP_1_2_CorrectFragmentedRequest()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 2            // giop minor
                                     );

        //manually write the first half of the string "barbaz"
        r_out.write_ulong( 7 ); //string length
        r_out.write_octet( (byte) 'b' );
        r_out.write_octet( (byte) 'a' );
        r_out.write_octet( (byte) 'r' );
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                     2 // giop minor
                                     );
        m_out.write_ulong( 0 ); // Fragment Header (request id)
        m_out.write_octet( (byte) 'b' );
        m_out.write_octet( (byte) 'a' );
        m_out.write_octet( (byte) 'z' );
        m_out.write_octet( (byte) 0);
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                                                    transport,
                                                    request_listener,
                                                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in = new RequestInputStream
            ( orb, null, request_listener.getRequest() );

        //is the body correct?
        assertEquals( "barbaz", r_in.read_string() );
    }

    public void testGIOP_1_0_CorrectRefusing()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     null,           //request id
                                     0,       //operation
                                     "foo",        //response expected
                                     true,   //SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply end time
                                     null, //object key
                                     new byte[1], 0            // giop minor
                                     );

        r_out.write_string( "bar" );
        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        GIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                                                    transport,
                                                    request_listener,
                                                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //no request or reply must have been handed over
        assertTrue( request_listener.getRequest() == null );
        assertTrue( reply_listener.getReply() == null );

        //instead, an error message have must been sent via the
        //transport
        assertTrue( transport.getWrittenMessage() != null );

        byte[] result = transport.getWrittenMessage();

        assertTrue( Messages.getMsgType( result ) == MsgType_1_1._MessageError );
        MessageOutputStream m_out =
            new MessageOutputStream(orb);
        m_out.writeGIOPMsgHeader( MsgType_1_1._Fragment,
                                     0 // giop minor
                                     );
        m_out.write_ulong( 0 ); // Fragment Header (request id)
        m_out.write_octet( (byte) 'b' );
        m_out.write_octet( (byte) 'a' );
        m_out.write_octet( (byte) 'z' );
        m_out.insertMsgSize();

        messages.add( m_out.getBufferCopy() );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //no request or reply must have been handed over
        assertTrue( request_listener.getRequest() == null );
        assertTrue( reply_listener.getReply() == null );

        //instead, an error message have must been sent via the
        //transport
        assertTrue( transport.getWrittenMessage() != null );

        //must be a new one
        assertTrue( transport.getWrittenMessage() != result );
        result = transport.getWrittenMessage();

        assertTrue( Messages.getMsgType( result ) == MsgType_1_1._MessageError );

    }

    public void testGIOP_1_1_IllegalMessageType()
    {
        List<byte[]> messages = new Vector<byte[]>();

        LocateRequestOutputStream r_out =
            new LocateRequestOutputStream(
                orb,
                new byte[1], //object key
                0,           //request id
                1            // giop minor
                );

        r_out.insertMsgSize();

        byte[] b = r_out.getBufferCopy();

        b[6] |= 0x02; //set "more fragments follow"

        messages.add( b );

//        MessageOutputStream m_out =
//            new MessageOutputStream();

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        GIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                                                    transport,
                                                    request_listener,
                                                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //no request or reply must have been handed over
        assertTrue( request_listener.getRequest() == null );
        assertTrue( reply_listener.getReply() == null );

        //instead, an error message have must been sent via the
        //transport
        assertTrue( transport.getWrittenMessage() != null );

        byte[] result = transport.getWrittenMessage();

        assertTrue( Messages.getMsgType( result ) == MsgType_1_1._MessageError );
    }

    public void testGIOP_1_2_CorrectCloseOnGarbage()
    {
        List<byte[]> messages = new Vector<byte[]>();

        String garbage = "This is a garbage message";
        byte[] b = garbage.getBytes();

        messages.add( b );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        GIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                                                    transport,
                                                    request_listener,
                                                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //no request or reply must have been handed over
        assertTrue( request_listener.getRequest() == null );
        assertTrue( reply_listener.getReply() == null );

        //instead, connection should be closed
        assertTrue( transport.hasBeenClosed() );

        //no message is written (makes no real sense)
        assertTrue( transport.getWrittenMessage() != null );
        assertTrue( transport.getWrittenMessage().length == 0 );
    }

    public void testGIOP_1_1_CorrectRequest()
    {
        List<byte[]> messages = new Vector<byte[]>();

        RequestOutputStream r_out =
            new RequestOutputStream( orb, //ClientConnection
                                     (ClientConnection) null,           //request id
                                     0,       //operation
                                     "foo",        // response expected
                                     true,   // SYNC_SCOPE (irrelevant)
                                     (short)-1,        //request start time
                                     null,        //request end time
                                     null,        //reply start time
                                     null, //object key
                                     new byte[1], 1            // giop minor
                                     );

        String message = "Request";
        r_out.write_string(message);
        r_out.insertMsgSize();

        messages.add( r_out.getBufferCopy() );

        DummyTransport transport =
            new DummyTransport( messages );

        DummyRequestListener request_listener =
            new DummyRequestListener();

        DummyReplyListener reply_listener =
            new DummyReplyListener();

        GIOPConnectionManager giopconn_mg =
            new GIOPConnectionManager();
        try
        {
            giopconn_mg.configure (config);
        }
        catch (Exception e)
        {
        }

        ServerGIOPConnection conn =
            giopconn_mg.createServerGIOPConnection( null,
                                                    transport,
                                                    request_listener,
                                                    reply_listener );

        try
        {
            //will not return until an IOException is thrown (by the
            //DummyTransport)
            conn.receiveMessages();
        }
        catch( IOException e )
        {
            //o.k., thrown by DummyTransport
        }
        catch( Exception e )
        {
            e.printStackTrace();
            fail( "Caught exception: " + e );
        }

        //did the GIOPConnection hand the complete request over to the
        //listener?
        assertTrue( request_listener.getRequest() != null );

        RequestInputStream r_in =
        new RequestInputStream( orb, null, request_listener.getRequest() );

        //is the body correct?
        assertEquals( message, r_in.read_string() );
    }

}// GIOPConnectionTest
