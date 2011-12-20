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

package javax.microedition.ims;

import javax.microedition.ims.common.DefaultTimeoutUnit;
import javax.microedition.ims.common.IMSEntityType;
import javax.microedition.ims.common.TimeoutUnit;
import javax.microedition.ims.core.StackContext;
import javax.microedition.ims.messages.wrappers.msrp.MsrpMessage;
import javax.microedition.ims.transport.MessageContentProvider;
import java.util.concurrent.TimeUnit;

/**
 * User: Pavel Laboda (pavel.laboda@gmail.com)
 * Date: 18-Feb-2010
 * Time: 15:41:14
 */
public class MsrpMessageContext extends DefaultMessageContext<MsrpMessage> {
    //private final StackContext context;


    public MsrpMessageContext(final StackContext context) {
        super(
                new MessageContentProvider<MsrpMessage>() {
                    
                    public String getContent(final MsrpMessage msg) {
                        if (msg.getBody() != null && msg.getBody().length > 4096) {
                            return "Long body message! \r\n " + msg.shortDescription();
                        }
                        else {
                            return msg.buildContent();
                        }
                    }

                    public byte[] getByteContent(MsrpMessage msg) {
                        return msg.buildByteContent();
                    }
                },
                new MsrpMessageReader(),
                new MsrpMessage()
        );
        //this.context = context;
    }

    
    public int getMessageRate() {
        return 30;
    }

    
    public MsrpMessage buildServiceUnavailableMessage(final MsrpMessage msg) {
        //SipMessageUtil<BaseSipMessage> sipMessageUtil = MessageUtilHolder.getSIPMessageUtil();
        return null;//sipMessageUtil.buildStatelessResponse(context, msg, StatusCode.SERVICE_UNAVAILABLE, null);
    }

    public MsrpMessage buildMessageTooLargeMessage(byte[] msg) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public TimeoutUnit getMessageLifeTime() {
        return new DefaultTimeoutUnit(32, TimeUnit.SECONDS);
    }

    public int getMessageHash(final MsrpMessage msg) {
        return msg.calcHash();
    }

    
    public IMSEntityType getEntityType() {
        return IMSEntityType.MSRP;
    }
}
