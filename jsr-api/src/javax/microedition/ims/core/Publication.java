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

import javax.microedition.ims.ServiceClosedException;

/**
 * The Publication is used for publishing event state to a remote endpoint. 
 * When a publication is created, an event package must be specified which 
 * identifies the type of content that is about to be published. A typical 
 * event package is 'presence' that can be used to publish online information. 
 * Other endpoints can then subscribe to that event package and get callbacks 
 * when the subscribed user identity changes online status.
 * <p/>
 * The published event state will be refreshed periodically until unpublish is called. 
 * Updates of the published event state may be achieved by calling the publish method again with modified event state.
 * <p/>
 * The life cycle consist of three states, STATE_INACTIVE, STATE_PENDING and STATE_ACTIVE.
 * <p/>
 * A new Publication starts in STATE_INACTIVE and when publish is called 
 * the state transits to STATE_PENDING and remains there until a response arrives from the remote endpoint.
 * <p/>
 * In STATE_ACTIVE, unpublish can be called to cancel the publication and 
 * the state will then transit to STATE_PENDING and remain there until a response arrives from the remote endpoint.
 * <p/>
 * Example of a simple Publication
 * <p/>
 * <pre>
 * 
 *   try {
 *     pub = service.createPublication(null, "sip:alice@home.net", "presence");
 *     pub.setListener(this); 
 *     pub.publish(onlineStatus, "application/pidf+xml");
 *   } catch(Exception e){
 *     //handle Exceptions
 *   }
 *     
 *   public void publicationDelivered(Publication pub){
 *     //if the publish was successfull
 *   }
 * </pre>
 * <p/>
 * Example of a multipart Publication
 * <p/>
 * <pre>
 * 
 *   try {
 *     pub = service.createPublication(null, "sip:alice@home.net", "presence");
 *     pub.setListener(this);
 *     Message message = pub.getNextRequest();
 *     // add first body part
 *     MessageBodyPart part1 = message.createBodyPart();
 *     part1.setHeader("Content-Type", "application/pidf+xml");
 *     OutputStream os = part1.openContentOutputStream();
 *     os.write(...);
 *     os.close();
 *     // add following body parts
 *     MessageBodyPart part2 = message.createBodyPart();
 *     ... 
 *     // when all body parts have been added
 *     pub.publish(null, null);
 *   } catch(Exception e){
 *     //handle Exceptions
 *   }
 *     
 *   public void publicationDelivered(Publication pub){
 *     // if the publish was successful
 *   }
 * </pre>
 */
public interface Publication extends ServiceMethod {
    /**
     * The Publication is not active, event state is not published. 
     */
    static final int STATE_INACTIVE = 1;
    
    /**
     * A Publication request is sent and the platform is waiting for a response.
     */
    static final int STATE_PENDING = 2;
    
    /**
     * The Publication is active and event state is available for subscription. 
     */
    static final int STATE_ACTIVE = 3;
    
    /**
     * Sends a publication request with an event state to the remote endpoint.
     * <p/>
     * This method can be invoked publish(null, null) if the application uses 
     * the Message interface to fill the event state. In the case that the application 
     * uses both the Message interface and publish(state, contentType), the event state will be added as the last body part.
     * <p/>
     * The Publication will transit to STATE_PENDING after calling this method. See example above.
     *  
     * @param state - event state to publish
     * @param contentType - the content MIME type
     * 
     * @throws IllegalStateException - if the Publication is in STATE_PENDING 
     * @throws ServiceClosedException - if the Service is closed
     * @throws IllegalArgumentException - if the syntax of the contentType argument is invalid or if one of the arguments is null
     */
    void publish(byte[] state, String contentType) throws ServiceClosedException;
    
    /**
     * Terminates this publication.
     * <p/>
     * The Publication will transit to STATE_PENDING after calling this method. 
     * 
     * @throws IllegalStateException - if the Publication is not in STATE_ACTIVE 
     * @throws ServiceClosedException - if the Service is closed
     */
    void unpublish() throws ServiceClosedException;
    
    /**
     * Sets a listener for this Publication, replacing any previous PublicationListener. 
     * A null reference is allowed and has the effect of removing any existing listener. 
     * 
     * @param listener - the listener to set, or null
     */
    void setListener(PublicationListener listener);
    
    /**
     * Returns the event package corresponding to this Publication. 
     * 
     * @return the event package
     */
    String getEvent();
    
    /**
     * Returns the current state of the state machine of this Publication. 
     * 
     * @return the current state
     */
    int getState();
    
}
