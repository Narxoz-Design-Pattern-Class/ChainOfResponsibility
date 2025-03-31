abstract class RequestHandler {
    protected RequestHandler next;

    public RequestHandler linkWith(RequestHandler next) {
        this.next = next;
        return next;
    }

    public abstract boolean handle(String request);
}

class AuthHandler extends RequestHandler {
    public boolean handle(String request) {
        if (!request.startsWith("Auth")) {
            System.out.println("Authentication failed.");
            return false;
        }
        System.out.println("Authentication passed.");
        return next == null || next.handle(request);
    }
}

class RateLimitHandler extends RequestHandler {
    public boolean handle(String request) {
        if (request.contains("TooManyRequests")) {
            System.out.println("Rate limit exceeded.");
            return false;
        }
        System.out.println("Rate check passed.");
        return next == null || next.handle(request);
    }
}

// Setup and usage:
RequestHandler handler = new AuthHandler();
handler.linkWith(new RateLimitHandler());

handler.handle("Auth:UserRequest"); // both pass
handler.handle("InvalidRequest:TooManyRequests"); // fails at auth