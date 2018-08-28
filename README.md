# OpenGDPR Android Client

OpenGDPR Android client is a module which will allow app developers to integrate the **OpenGDPR Api** as specified in their [homepage](https://www.opengdpr.org).

Currently many partners implement their own ways to manage user consent and process the requests coming from data subjects themselves or from third-party vendors which share personal information about their users. **OpenGDPR Client** complies with the standard defined by **OpenGDPR** to have a common way to communicate between partners and therefore makes room for a seamless integration of new vendors.

## Contents

* [Integration](#integration)
* [Usage](#usage)
    * [Open GDPR Discovery](#opengdpr-discovery)
    * [Open GDPR Request](#opengdpr-request)
    * [Open GDPR Request Status](#opengdpr-status)
    * [Open GDPR Request Cancellation](#opengdpr-cancel)
* [Pending features](#pending-features)
* [Misc](#misc)
    * [License](#misc_license)
    * [Contributing](#misc_contributing)

<a name="integration"></a>
# Integration

To integrate the **OpenGDPR** client, you can add the **opengdpr.client** module from the repo to your own Android project.

<a name="usage"></a>
# Usage

To define the url of the OpenGDPR Api you will be using you can modify the constants in the **OpenGDPREndpoints** class. You can change the base url and add or remove path components if required.

<a name="opengdpr-discovery"></a>
### Open GDPR Discovery

The discovery endpoint of the OpenGDPR Api specification returns the identity and request types supported by the server and a download link to the public key that can be used to validate the Signature received on the response headers.

To make a request to the discovery endpoint you should use **OpenGDPRDiscoveryClient**.

``` Java
OpenGDPRDiscoveryClient client = new OpenGDPRDiscoveryClient();
client.fetchDiscoveryData(this, new OpenGDPRDiscoveryClient.DiscoveryListener() {
    @Override
    public void onSuccess(DiscoveryResponseModel model) {
        try {
            // Handle response
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onFailure(Throwable error) {
        Log.e(TAG, error.getMessage());
    }
});
```

Up to the current version the client is not caching the certificate that this endpoint receives, therefore it needs to be cached in memory if the response signature will be checked.

<a name="opengdpr-request"></a>
### Open GDPR Request

The request endpoint of the OpenGDPR Api specification allows the client to place a request for access, portability or erasure of a data subject's info. 

Currently the client is not caching the request and identity type information retrieved in the discovery endpoint, therefore it won't fail automatically if some invalid request or identity is passed.

You should use the **RequestIdentityModel** to create the identities that will be passed to the request.

In the interfaces **IdentityType** and **IdentityFormat** you can find the constants needed to define the type and format of the identities provided. It is recommended to use these constants since they contain all the supported params defined by the OpenGDPR specification and to avoid typos. For the hashes the class **Crypto** can be used. It provides the required methods to turn String into **sha1**, **md5** and **sha256** hashes.

``` Java
String mail = "test@mail.com";
RequestIdentityModel identity1 = new RequestIdentityModel();
identity1.setIdentityType(IdentityType.EMAIL);
identity1.setIdentityFormat(IdentityFormat.RAW);
identity1.setIdentityValue(mail);

RequestIdentityModel identity2 = new RequestIdentityModel();
identity2.setIdentityType(IdentityType.EMAIL);
identity2.setIdentityFormat(IdentityFormat.MD5);
identity2.setIdentityValue(Crypto.md5(mail));
```

To make a request to the endpoint you should use **OpenGDPRRequestClient**.

In the interface **RequestType** you can find the constants needed to define the request type. It is recommended to use these constants since they contain all the supported params defined by the OpenGDPR specification and to avoid typos.

The client will automatically generate a **requestId** but if you want to provide your own UUID you can call the function **setRequestId(String requestId)** of the **OpenGDPRRequest**.

``` Java
OpenGDPRRequest request = new OpenGDPRRequest();
request.addIdentity(identity1);
request.addIdentity(identity2);
request.setType(RequestType.ACCESS);
//optional
request.setRequestId(UUID.randomUUID().toString());


OpenGDPRRequestClient client = new OpenGDPRRequestClient();
client.doRequest(this, request, new OpenGDPRRequestClient.RequestListener() {
    @Override
    public void onSuccess(OpenGDPRResponseModel model) {
        try {
            // Handle response
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onFailure(Throwable error) {
        Log.e(TAG, error.getMessage());
    }
});
```

The client is currently not doing any response handling so the **requestId** must be cached in order to be used later if the **Status** or **Cancellation** endpoints will be called.

<a name="opengdpr-status"></a>
### Open GDPR Request Status

The status endpoint of the OpenGDPR Api specification should return the current state of a request based on the **requestId** provided.

To make a request to the status endpoint you should use **OpenGDPRStatusClient**.

``` Java
OpenGDPRStatusClient client = new OpenGDPRStatusClient();
client.fetchRequestStatus(this, <REQUEST_ID>, new OpenGDPRStatusClient.StatusListener() {
    @Override
    public void onSuccess(StatusResponseModel model) {
        try {
            // Handle response
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onFailure(Throwable error) {
        Log.e(TAG, error.getMessage());
    }
});
```

<a name="opengdpr-cancel"></a>
### Open GDPR Request Cancellation

The cancellation endpoint of the OpenGDPR Api specification should allow the client to cancel a request with the provided **requestId**.

To make a request to the cancellation endpoint you should use **OpenGDPRCancellationClient**.

``` Java
OpenGDPRCancellationClient client = new OpenGDPRCancellationClient();
client.doCancellationRequest(this, <REQUEST_ID>, new OpenGDPRCancellationClient.CancellationListener() {
    @Override
    public void onSuccess(CancellationResponseModel model) {
        try {
            String content = model.toJson().toString();
            mResultView.setText(content);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onFailure(Throwable error) {
        Log.e(TAG, error.getMessage());
    }
});
```

<a name="pending-features"></a>
# Pending features

Based to the OpenGDPR specification, a few features are still missing to be fully compliant with the definition.

* Cache the public key retrieved from the **Discover** endpoint.
* Check the signature received on the response headers using the public key.
* Cache the supported identity types retrieved from the **Discovery** endpoint to do a request validation before sending to the server.
* Cache the supported request types retrieved from the **Discovery** endpoint to do a request validation before sending to the server.

<a name="misc"></a>
# Misc

<a name="misc_license"></a>
### License

This code is distributed under the terms and conditions of the MIT license.

<a name="misc_contributing"></a>
### Contributing

If you fix a bug you discovered or have development ideas, feel free to make a pull request.