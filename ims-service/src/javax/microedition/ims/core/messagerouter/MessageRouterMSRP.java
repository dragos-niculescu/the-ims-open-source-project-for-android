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

package javax.microedition.ims.core.messagerouter;

import javax.microedition.ims.common.IMSEntityType;
import javax.microedition.ims.common.IMSMessage;
import javax.microedition.ims.common.Logger;
import javax.microedition.ims.transport.messagerouter.Route;
import javax.microedition.ims.transport.messagerouter.RouteDescriptor;
import javax.microedition.ims.transport.messagerouter.RouterEventDefaultImpl;
import javax.microedition.ims.util.MessageUtilHolder;
import javax.microedition.ims.util.MsrpMessageUtil;
import java.util.*;

/**
 * User: Pavel Laboda (pavel.laboda@gmail.com)
 * Date: 16.8.2010
 * Time: 17.48.06
 */
public class MessageRouterMSRP extends MessageRouterBase<IMSMessage> {
    private final Map<RouteKey, Route> sendMsrpRouteMap;


    public MessageRouterMSRP() {
        this.sendMsrpRouteMap = Collections.synchronizedMap(new HashMap<RouteKey, Route>());
    }

    public IMSEntityType getEntityType() {
        return IMSEntityType.MSRP;
    }

    public Route getRoute(final IMSMessage msg) {
        Route retValue = null;
        if (msg.getEntityType() == IMSEntityType.MSRP) {

            final MsrpMessageUtil<IMSMessage> msrpMessageUtil = MessageUtilHolder.getMSRPMessageUtil();

            String id = msrpMessageUtil.getFromPathID(msg);
            retValue = sendMsrpRouteMap.get(new RouterKeyDeafultImpl(id));
            if (retValue == null) {
                id = msrpMessageUtil.getToPathID(msg);
                retValue = sendMsrpRouteMap.get(new RouterKeyDeafultImpl(id));
            }
            Logger.log("getRoute. route: " + retValue + " id:" + id + " for message: \n" + msg.buildContent());
        }
        else {
            final String errMsg = "Wrong message type. Must be " + IMSEntityType.MSRP +
                    " now is " + msg.getEntityType() + " message=" + msg.shortDescription();

            assert false : errMsg;
        }

        return retValue;
    }

    public void addRoute(final Route route, final RouteDescriptor routeDescriptor) {
        if (route.getEntityType() == IMSEntityType.MSRP) {
            //TODO: add handling for closing not used MSRP connections and support for simultaneous MSRP connections
            Logger.log("addRoute. Route : " + route + " id: " + routeDescriptor.getKey());
            sendMsrpRouteMap.put(routeDescriptor.getKey(), route);
            getListenerHolder().getNotifier().onRouteAdded(new RouterEventDefaultImpl(route));
        }
    }

    public Route removeRoute(final RouteKey key) {
        Route retValue = sendMsrpRouteMap.remove(key);
        Logger.log("removeRoute. route: " + retValue + "id:" + key);
        if (retValue != null) {
            getListenerHolder().getNotifier().onRouteRemoved(new RouterEventDefaultImpl(retValue));
        }
        return retValue;
    }

    public Collection<Route> getActiveRoutes() {
        synchronized (sendMsrpRouteMap) {
            return new ArrayList<Route>(sendMsrpRouteMap.values());
        }
    }
}
