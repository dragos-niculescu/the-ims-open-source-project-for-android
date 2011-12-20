/*
 * This software code is (c) 2010 T-Mobile USA, Inc. All Rights Reserved.
 *
 * Unauthorized redistribution or further use of this material is
 * prohibited without the express permission of T-Mobile USA, Inc. and
 * will be prosecuted to the fullest extent of the law.
 *
 * Removal or modification of these Terms and Conditions from the source
 * or binary code of this software is prohibited.  In the event that
 * redistribution of the source or binary code for this software is
 * approved by T-Mobile USA, Inc., these Terms and Conditions and the
 * above copyright notice must be reproduced in their entirety and in all
 * circumstances.
 *
 * No name or trademarks of T-Mobile USA, Inc., or of its parent company,
 * Deutsche Telekom AG or any Deutsche Telekom or T-Mobile entity, may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" AND "WITH ALL FAULTS" BASIS
 * AND WITHOUT WARRANTIES OF ANY KIND.  ALL EXPRESS OR IMPLIED
 * CONDITIONS, REPRESENTATIONS OR WARRANTIES, INCLUDING ANY IMPLIED
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT CONCERNING THIS SOFTWARE, ITS SOURCE OR BINARY CODE
 * OR ANY DERIVATIVES THEREOF ARE HEREBY EXCLUDED.  T-MOBILE USA, INC.
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE
 * OR ITS DERIVATIVES.  IN NO EVENT WILL T-MOBILE USA, INC. OR ITS
 * LICENSORS BE LIABLE FOR LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES,
 * HOWEVER CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT
 * OF THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF T-MOBILE USA,
 * INC. HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * THESE TERMS AND CONDITIONS APPLY SOLELY AND EXCLUSIVELY TO THE USE,
 * MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE, ITS SOURCE OR BINARY
 * CODE OR ANY DERIVATIVES THEREOF, AND ARE SEPARATE FROM ANY WRITTEN
 * WARRANTY THAT MAY BE PROVIDED WITH A DEVICE YOU PURCHASE FROM T-MOBILE
 * USA, INC., AND TO THE EXTENT PERMITTED BY LAW.
 */

package javax.microedition.ims.core;

/**
 * The <code>Message</code> interface provides functionality to manipulate
 * headers and body parts of SIP messages.
 * </p><p>
 * <p/>
 * This is an interface for use in advanced applications.
 * </p><p>
 * A <code>Message</code> can be retrieved by calling
 * <code>ServiceMethod.getNextRequest</code>,
 * <code>ServiceMethod.getPreviousRequest</code>,
 * <code>ServiceMethod.getNextResponse</code> or
 * <code>ServiceMethod.getPreviousResponses</code>.
 * </p><p>
 * <p/>
 * </p><h3>Modifying headers</h3>
 * The IMSAPI allows the application to read and write to SIP message headers.
 * A header with no predefined meaning or standard can be read and written
 * without restriction. If the header is covered in a standard, then any
 * modification to the header must be in accordance to the standard.
 * <p/>
 * <h4>Modifying the 'Content-Type' header</h4>
 * The 'Content-Type' header can only be set on messages with more than one
 * bodypart, and only to 'multipart/*'. See also section below on Modifying
 * message bodies.
 * <p/>
 * </p><h3>Modifying message bodies</h3>
 * The interface allows the application to create non-recursive multi-part
 * SIP messages bodies. The following shows how the object structure is mapped
 * to the SIP message structure. For the sending side:
 * <ul>
 * <li>If a <code>Message</code> has one <code>MessageBodyPart</code> then the
 * <code>Message</code> will have the body
 * content and content type set to the <code>MessageBodyPart</code> content
 * and content type.</li>
 * <li>If a <code>Message</code> has several <code>MessageBodyParts</code>, then
 * each <code>MessageBodyPart</code> (
 * recursive <code>MessageBodyPart</code> are not possible) is mapped to a
 * part in the
 * SIP message body (content and headers, including the content type).
 * The message body content type is by default set to multipart/mixed, but can
 * be overridden by the application to be of another subtype to multipart.<br>
 * <p/>
 * NOTE: For the case where the IMS core has an SDP attached to the SIP
 * message, then that is handled as if it were a <code>MessageBodyPart</code> not
 * accessible to the application.</li>
 * </ul>
 * For the receiving side:
 * <ul>
 * <li>A SIP message with a body where content type is not multipart is handled
 * as a <code>Message</code> with one <code>MessageBodyPart</code> and
 * that content type.</li>
 * <li>A multipart message (of any subtype) is parsed and each part on the
 * top level is mapped to a <code>MessageBodyPart</code>
 * (nested multi-part messages cannot be mapped to recursive
 * <code>MessageBodyParts</code>). <br>
 * <p/>
 * Note: Any SDP part discovered here shall not be mapped to a
 * <code>MessageBodyPart</code> accessible to the application, unless it is
 * part of an OPTIONS response.</li>
 * </ul>
 * <p>
 * See [RFC3261] for more information about headers and body parts in a request
 * messages.
 * </p><p>
 * </p><p>
 * <p/>
 * </p>
 *
 * @see ServiceMethod
 */

public interface Message {
    /**
     * Identifier for the queryCapabilities method on the Capabilities interface.
     */
    static final int CAPABILITIES_QUERY = 1;

    /**
     * Identifier for the send method on the PageMessage interface.
     */
    static final int PAGEMESSAGE_SEND = 2;

    /**
     * Identifier for the publish method on the Publication interface.
     */
    static final int PUBLICATION_PUBLISH = 3;

    /**
     * Identifier for the unpublish method on the Publication interface.
     */
    static final int PUBLICATION_UNPUBLISH = 4;

    /**
     * Identifier for the refer method on the Reference interface.
     */

    static final int REFERENCE_REFER = 5;
    /**
     * Identifier for the start method on the Session interface.
     */
    static final int SESSION_START = 6;

    /**
     * Identifier for the terminate method on the Session interface.
     */
    static final int SESSION_TERMINATE = 8;

    /**
     * Identifier for the update method on the Session interface.
     */
    static final int SESSION_UPDATE = 7;

    /**
     * This state specifies that this Message was received from a remote endpoint.
     */
    static final int STATE_RECEIVED = 3;

    /**
     * This state specifies that the Message is sent from the local endpoint.
     */
    static final int STATE_SENT = 2;

    /**
     * This state specifies that the Message is unsent.
     */
    static final int STATE_UNSENT = 1;

    /**
     * Identifier for the poll method on the Subscription interface.
     */
    static final int SUBSCRIPTION_POLL = 11;

    /**
     * Identifier for the subscribe method on the Subscription interface.
     */
    static final int SUBSCRIPTION_SUBSCRIBE = 9;

    /**
     * Identifier for the unsubscribe method on the Subscription interface.
     */
    static final int SUBSCRIPTION_UNSUBSCRIBE = 10;

    /**
     * Adds a header value, either on a new header or appending a new value to an already existing header.
     *
     * @param key
     * @param value
     */
    void addHeader(String key, String value);

    /**
     * Creates a new MessageBodyPart and adds it to the message.
     *
     * @return
     */
    MessageBodyPart createBodyPart();

    /**
     * Returns all body parts that are added to the message.
     *
     * @return
     */
    MessageBodyPart[] getBodyParts();

    /**
     * Returns the value(s) of a header in this message.
     *
     * @param key
     * @return
     */
    String[] getHeaders(String key);

    /**
     * Returns the SIP method for this Message.
     *
     * @return
     */
    String getMethod();

    /**
     * Returns the reason phrase of the response.
     *
     * @return
     */
    String getReasonPhrase();

    /**
     * Returns the current state of this Message.
     *
     * @return
     */
    int	getState();
    
	/**
     * Returns the status code of the response.
     *
     * @return
     */
	int getStatusCode();
    
	
}
