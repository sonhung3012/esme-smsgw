/*
 * Copyright (c) 1996-2001
 * Logica Mobile Networks Limited
 * All rights reserved.
 *
 * This software is distributed under Logica Open Source License Version 1.0
 * ("Licence Agreement"). You shall use it and distribute only in accordance
 * with the terms of the License Agreement.
 *
 */
package com.fis.esme.core.smpp;

import java.io.IOException;
 
import org.smpp.Connection;
import org.smpp.Receiver;
import org.smpp.Transmitter;
import org.smpp.SmppObject;
import org.smpp.pdu.*;

/**
 * This class represent one client connection to the server starting
 * by accepting the connection, authenticating of the client,
 * communication and finished by unbinding.
 * The <code>SMSCSession</code> object is generated by <code>SMSCListener</code>
 * which also sets the session's PDU processor. Session is run in separate
 * thread; it reads PDUs from the connection and calls PDU processor's
 * client methods to process the received PDUs. PDU processor on turn can
 * use the session to submit PDUs to the client.
 * For receiving and sending of PDUs the session uses instances of
 * <code>Receiver</code> and <code>Transmitter</code>.
 *
 * @author Logica Mobile Networks SMPP Open Source Team
 * @version 1.0, 21 Jun 2001
 * @see SMSCListener
 * @see PDUProcessor
 * @see Connection
 * @see Receiver
 * @see Transmitter
 */
public class SMSCSession extends SmppObject
implements Runnable
{
    private Receiver receiver;
    private Transmitter transmitter;
    private PDUProcessor pduProcessor;
    private Connection connection;
//    private long receiveTimeout = Data.RECEIVER_TIMEOUT;
    private long receiveTimeout = 5000;
    private boolean keepReceiving = true;
    private boolean isReceiving = false;
    private long lastRequestTime;

    /**
     * Initialises the session with the connection the session
     * should communicate over.
     * @param connection the connection object for communication with client
     */
    public SMSCSession(Connection connection)
    {
        this.connection = connection;
        transmitter = new Transmitter(connection);
        receiver = new Receiver(transmitter, connection);
        updateLastRequestTime();
    }

    /**
     * Signals the session's thread that it should stop.
     * Doesn't wait for the thread to be completly finished.
     * Note that it can take some time before the thread is completly
     * stopped.
     * @see #run()
     */
    public void stop()
    {
        debug.write("SMSCSession stopping");
        keepReceiving = false;
    }
    
    /**
     * Implements the logic of receiving of the PDUs from client and passing
     * them to PDU processor. First starts receiver, then in cycle
     * receives PDUs and passes them to the proper PDU processor's
     * methods. After the function <code>stop</code> is called (externally)
     * stops the receiver, exits the PDU processor and closes the connection,
     * so no extry tidy-up routines are necessary.
     * @see #stop()
     * @see PDUProcessor#clientRequest(Request)
     * @see PDUProcessor#clientResponse(Response)
     */
    public void run() 
    {
        PDU pdu = null;
    
        debug.enter(this,"SMSCSession run()");
        debug.write("SMSCSession starting receiver");
        receiver.start();
        isReceiving = true;
        try {
            while (keepReceiving)
            {
                try {
                    debug.write("SMSCSession going to receive a PDU");
                    pdu = receiver.receive(getReceiveTimeout());
                } catch (Exception e){
                    debug.write("SMSCSession caught exception receiving PDU " + e.getMessage());
                }

                if (pdu != null) {
                    if (pdu.isRequest()) {
                        debug.write("SMSCSession got request " + pdu.debugString());
                        pduProcessor.clientRequest((Request)pdu);
                    } else if (pdu.isResponse()) {
                        debug.write("SMSCSession got response " + pdu.debugString());
                        pduProcessor.clientResponse((Response)pdu);
                    } else {
                        debug.write("SMSCSession not reqest nor response => not doing anything.");
                    }
                }
            }
        } finally {
            isReceiving = false;
        }
        debug.write("SMSCSession stopping receiver");
        receiver.stop();
        debug.write("SMSCSession exiting PDUProcessor");
        pduProcessor.exit();
        try {
            debug.write("SMSCSession closing connection");
            connection.close();
        } catch (IOException e) {
            event.write(e, "closing SMSCSession's connection.");
        }
        debug.write("SMSCSession exiting run()");
        debug.exit(this);
    }

    /**
     * Sends a PDU to the client.
     * @param pdu the PDU to send
     */
    public void send(PDU pdu)
    {
        try {
            debug.write("SMSCSession going to send pdu over transmitter");
            transmitter.send(pdu);
            debug.write("SMSCSession pdu sent over transmitter");
        } catch (ValueNotSetException e) {
            event.write(e, "");
        } catch (IOException e) {
            event.write(e, "");
        }
    }
    
    /**
     * Sets new PDU processor.
     * @param pduProcessor the new PDU processor
     */
    public void setPDUProcessor(PDUProcessor pduProcessor)
    {
        this.pduProcessor = pduProcessor;
    }

    /**
     * Sets the timeout for receiving the complete message.
     * @param timeout the new timeout value
     */
    public void setReceiveTimeout(long timeout)
    {
        receiveTimeout = timeout;
    }

    /**
     * Returns the current setting of receiving timeout.
     * @return the current timeout value
     */
    public long getReceiveTimeout()
    {
        return receiveTimeout;
    }
    
    public long getLastRequestTime() {
    	return lastRequestTime;
    }
    
    public void updateLastRequestTime() {
    	lastRequestTime = System.currentTimeMillis();
    }
}
