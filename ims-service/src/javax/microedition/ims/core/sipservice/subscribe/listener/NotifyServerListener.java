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

package javax.microedition.ims.core.sipservice.subscribe.listener;

import javax.microedition.ims.common.ListenerHolder;
import javax.microedition.ims.common.ListenerSupport;
import javax.microedition.ims.core.dialog.Dialog;
import javax.microedition.ims.core.dialog.IncomingNotifyListener;
import javax.microedition.ims.core.sipservice.ReferState;
import javax.microedition.ims.core.sipservice.TransactionStateChangeEvent;
import javax.microedition.ims.core.sipservice.subscribe.NotifyInfo;
import javax.microedition.ims.core.transaction.TransactionListener;
import javax.microedition.ims.core.transaction.UnSubscribeOnLogicCompleteAdapter;
import javax.microedition.ims.util.MessageUtilHolder;

public class NotifyServerListener<T> extends UnSubscribeOnLogicCompleteAdapter<T> {
    private final Dialog dialog;
    private final ListenerHolder<IncomingNotifyListener> listenerHolder;

    public NotifyServerListener(
            final Dialog dialog,
            final ListenerSupport<TransactionListener<T>> listenerSupport,
            final ListenerHolder<IncomingNotifyListener> listenerHolder) {
        super(listenerSupport);

        this.dialog = dialog;
        this.listenerHolder = listenerHolder;
    }

    
    public void onStateChanged(final TransactionStateChangeEvent<T> event) {
        //do listener un-subscribe here
        super.onStateChanged(event);

//        assert Dialog.InitiateParty.REMOTE == DIALOG.getInitiateParty();

        ReferState sessionState = ReferState.toReferState(event);

        if (ReferState.NOTIFICATION_RECEIVED == sessionState) {

            //As far as we detected server media update we can un-subscribe form listening state changes
            doUnSubscribe(event.getTransaction());

            //Request lastNotifyRequest = DIALOG.getMessageHistory().getLastRequestByMethod(MessageType.SIP_NOTIFY);
            NotifyInfo notifyInfo = getNotifyInfo(event.getTriggeringMessage());

            listenerHolder.getNotifier().notificationReceived(new NotifyEvent(dialog, notifyInfo));
        }
    }

    private NotifyInfo getNotifyInfo(T msg) {
        return MessageUtilHolder.getSIPMessageUtil().getNotifyInfo(msg);
    }

    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("NotifyServerListener");
        sb.append("{DIALOG=").append(dialog);
        sb.append('}');
        return sb.toString();
    }
}