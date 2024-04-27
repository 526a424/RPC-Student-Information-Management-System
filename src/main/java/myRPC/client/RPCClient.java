package myRPC.client;

import myRPC.common.RPCRequest;
import myRPC.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest request);
}
