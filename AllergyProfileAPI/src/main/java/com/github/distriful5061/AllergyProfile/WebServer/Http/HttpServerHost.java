package com.github.distriful5061.AllergyProfile.WebServer.Http;

import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.AbstractBaseHandler;
import com.github.distriful5061.AllergyProfile.Utils.GsonUtils;
import com.github.distriful5061.AllergyProfile.Utils.Log.LogLevel;
import com.github.distriful5061.AllergyProfile.Utils.Log.LogUtils;
import com.github.distriful5061.AllergyProfile.Utils.ResourceUtils;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.ContentType;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.Header.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response.BasicError;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Gson.Response.StatusCodeOnly;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Httpサーバーをホストしているクラス
 *
 * @since 1.0
 */
public class HttpServerHost implements Runnable {
    private final int PORT;
    public HttpServerHost(int port) {
        PORT = port;
    }
    private static final Map<String, AbstractBaseHandler> handlerList = new HashMap<>();

    /**
     * AbstractBaseHandlerベースのハンドラーを追加するメソッド。これに追加すると、アクセスが有ったときにそのハンドラーを起動する。なんか失敗したら500返す
     *
     * @param path アクセスされたときのパス。rootなら/、aaaなら/aaa
     * @param baseHandler 起動されるハンドラー
     * @see AbstractBaseHandler
     */
    public static void addHandler(String path, AbstractBaseHandler baseHandler) {
        String addedPath = path + "|" + baseHandler.getSupportedMethod();
        if (handlerList.containsKey(addedPath)) {
            handlerList.replace(addedPath, baseHandler);
            return;
        }
        handlerList.put(addedPath, baseHandler);
    }

    /**
     * AbstractBaseHandlerベースのハンドラーをリストから削除するメソッド
     *
     * @param path そのハンドラーが割り当てられているpath
     * @param httpMethod 対応しているメソッド。
     * @return そのabstractBaseHandlerのインスタンス
     * @see AbstractBaseHandler
     */
    public static AbstractBaseHandler deleteHandler(String path, HttpMethod httpMethod) {
        AbstractBaseHandler tmp = null;

        String addPath = path + "|" + httpMethod;

        if (handlerList.containsKey(addPath)) {
            tmp = handlerList.get(addPath);
            handlerList.remove(addPath);
        }
        return tmp;
    }

    /**
     * スタッツコードと、Jsonに変換できるクラス(インスタンス)を使用することで、簡単にjsonとして返信ができるメソッド。
     *
     * @param outputStream 送信先stream
     * @param httpStatusCode ステータスコード
     * @param jsonObject Jsonに変換できるオブジェクト
     * @throws IOException 送信に失敗しました。
     */
    public static void throwSimpleCodeWithJson(OutputStream outputStream, HttpStatusCode httpStatusCode, Object jsonObject) throws IOException {
        if (jsonObject == null) jsonObject = new StatusCodeOnly(httpStatusCode);

        Map<String, Object> headers = HttpResponse.getDefaultHeader();
        HttpResponse httpResponse = new HttpResponse();

        headers.put("content-type", ContentType.APPLICATION_JSON);

        String body = GsonUtils.toJson(jsonObject);

        httpResponse.addHeader(headers);
        httpResponse.setStatusCode(httpStatusCode);
        httpResponse.setBody(body);
        httpResponse.write(outputStream);
    }

    /**
     * OutputStream, HttpStatusCode, Bodyだけで簡単なスタッツコード形式のレスポンスの返答が可能。
     *
     * @param outputStream 送信先stream
     * @param httpStatusCode スタッツコード
     * @param body ボディ
     * @throws IOException 送信に失敗しました。
     */
    public static void throwSimpleCode(OutputStream outputStream, HttpStatusCode httpStatusCode, String body) throws IOException {
        if (body == null) body = "";

        Map<String, Object> headers = HttpResponse.getDefaultHeader();
        HttpResponse httpResponse = new HttpResponse();

        headers.put("content-type", ContentType.TEXT_PLAIN);

        httpResponse.addHeader(headers);
        httpResponse.setStatusCode(httpStatusCode);
        httpResponse.setBody("%d %s\n%s".formatted(httpStatusCode.getStatusCode(), httpStatusCode.getExtension(), body));
        httpResponse.write(outputStream);
    }

    /**
     * スタッツコードだけで簡易的な返答を行うメソッド
     *
     * @param outputStream 送信先stream
     * @param httpStatusCode スタッツコード
     * @throws IOException 送信に失敗しました。
     */
    public static void throwSimpleCode(OutputStream outputStream, HttpStatusCode httpStatusCode) throws IOException {
        throwSimpleCode(outputStream, httpStatusCode, "");
    }
    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (Throwable e) {
            LogUtils.println("ServerSocket boot has failed.", LogLevel.FATAL);
            LogUtils.println(e.getMessage(), LogLevel.TRACE);
            return;
        }


        try {
            for (; ; ) {
                try {
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    OutputStream outputStream = socket.getOutputStream();

                    Runnable socketProcessor = () -> {// Runnableのまま放置しといてください
                        try {
                            boolean flag = true;
                            HttpRequest httpRequest = new HttpRequest(inputStream);
                            String path = httpRequest.getHeader().getPath();
                            HttpMethod requestMethod = httpRequest.getHeader().getMethod();
                            String hostAddress = socket.getInetAddress().getHostAddress();

                            LogUtils.println("Connection %s -> Me | Method:%s Path:%s".formatted(hostAddress, requestMethod.toString(), path));

                            if (requestMethod == HttpMethod.GET) {// GET以外のメソッドはこの後実装します
                                String addPath = path + "|" + HttpMethod.GET;
                                if (handlerList.containsKey(addPath)) {
                                    flag = false;

                                    AbstractBaseHandler abstractBaseHandler = handlerList.get(addPath);

                                    try {
                                        abstractBaseHandler.run(inputStream, outputStream, httpRequest);
                                        LogUtils.println("Connection %s <- Me | Response by Handler".formatted(hostAddress));
                                    } catch (Throwable e) {
                                        LogUtils.println("Error at GET Method Handlers Area", LogLevel.ERROR);
                                        LogUtils.println(e.getMessage(), LogLevel.TRACE);
                                        throw new IOException(e.getMessage());
                                    }
                                } else if (path.startsWith("/resources/")) {
                                    int resourcesPathLength = "/resources/".length();
                                    String cutString = path.substring(resourcesPathLength).replace("../", "");

                                    File file = ResourceUtils.getFileResourcesByName("resource/" + cutString);

                                    if (file.exists() && file.isFile()) {
                                        flag = false;
                                        HttpResponse httpResponse = new HttpResponse();
                                        Map<String, Object> headers = HttpResponse.getDefaultHeader();

                                        httpResponse.setStatusCode(HttpStatusCode.OK);
                                        httpResponse.addHeader(headers);
                                        httpResponse.setBody(file);

                                        try {
                                            httpResponse.write(outputStream);
                                            LogUtils.println("Connection %s <- Me | Msg: Resource Access StatusCode: %d ResourceTarget: %s".formatted(hostAddress, httpResponse.getStatusCode().getStatusCode(),cutString));
                                        } catch (IOException e) {
                                            LogUtils.println("Error at Resource Area", LogLevel.ERROR);
                                            LogUtils.println(e.getMessage(), LogLevel.TRACE);
                                        }
                                    }
                                }
                            } else if (requestMethod == HttpMethod.POST) {
                                String addPath = path + "|" + HttpMethod.POST;

                                LogUtils.println("Request Body(%s) \u21BB Me: %s".formatted(hostAddress, httpRequest.getBody()));

                                if (handlerList.containsKey(addPath)) {
                                    flag = false;

                                    AbstractBaseHandler abstractBaseHandler = handlerList.get(addPath);

                                    try {
                                        abstractBaseHandler.run(inputStream, outputStream, httpRequest);
                                        LogUtils.println("Connection %s <- Me | Response by Handler".formatted(hostAddress));
                                    } catch (Throwable e) {
                                        LogUtils.println("Error at POST Method Handlers Area", LogLevel.ERROR);
                                        LogUtils.println(e.getMessage(), LogLevel.TRACE);
                                        throw new IOException(e.getMessage());
                                    }
                                }
                            } else if (requestMethod == HttpMethod.PUT) {

                            } else if (requestMethod == HttpMethod.DELETE) {

                            }

                            if (flag) {
                                if (requestMethod == HttpMethod.GET) {
                                    throwSimpleCode(outputStream, HttpStatusCode.NOT_FOUND);
                                    LogUtils.println("Connection %s <- Me | Msg: Not Found, StatusCode: 404".formatted(hostAddress));
                                } else if (requestMethod == HttpMethod.POST) {
                                    BasicError basicError = new BasicError(HttpStatusCode.BAD_REQUEST, null, requestMethod);

                                    throwSimpleCodeWithJson(outputStream, HttpStatusCode.BAD_REQUEST, basicError);
                                    LogUtils.println("Connection %s <- Me | Msg: Bad Request, StatusCode: 400".formatted(hostAddress));
                                } else if (requestMethod == HttpMethod.PUT) {
                                    BasicError basicError = new BasicError(HttpStatusCode.BAD_REQUEST, null, requestMethod);

                                    throwSimpleCodeWithJson(outputStream, HttpStatusCode.BAD_REQUEST, basicError);
                                    LogUtils.println("Connection %s <- Me | Msg: Bad Request, StatusCode: 400".formatted(hostAddress));
                                } else if (requestMethod == HttpMethod.DELETE) {
                                    BasicError basicError = new BasicError(HttpStatusCode.BAD_REQUEST, null, requestMethod);

                                    throwSimpleCodeWithJson(outputStream, HttpStatusCode.BAD_REQUEST, basicError);
                                    LogUtils.println("Connection %s <- Me | Msg: Bad Request, StatusCode: 400".formatted(hostAddress));
                                } else {
                                    BasicError basicError = new BasicError(HttpStatusCode.BAD_REQUEST, "Bad Method", requestMethod);

                                    throwSimpleCodeWithJson(outputStream, HttpStatusCode.BAD_REQUEST, basicError);
                                    LogUtils.println("Connection %s <- Me | Msg: Bad Request, StatusCode: 400".formatted(hostAddress));
                                }
                            }

                        } catch (IOException ignored) {
                            String host = socket.getInetAddress().getHostAddress();
                            try {
                                throwSimpleCode(outputStream, HttpStatusCode.INTERNAL_SERVER_ERROR);
                                LogUtils.println("Connection %s <- Me | Msg: Internal Server Error, StatusCode: 500".formatted(host));
                            } catch (IOException ignored1) {
                                LogUtils.println("Connection Failed | %s".formatted(host));
                            }
                        } finally {
                            try {
                                socket.close();
                                inputStream.close();
                                outputStream.close();
                            } catch (IOException ignored1) {
                                LogUtils.println("Socket's Close failed", LogLevel.ERROR);
                            }
                        }
                    };
                    new Thread(socketProcessor).start();

                } catch (IOException e) {
                    LogUtils.println("IO Exception", LogLevel.ERROR);
                    LogUtils.println(e.getMessage(), LogLevel.TRACE);
                }
            }
        } catch (Throwable e) {
            LogUtils.println("Fatal Error", LogLevel.FATAL);
            LogUtils.println(e.getMessage(), LogLevel.TRACE);

            try {
                serverSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            e.printStackTrace();
        }
        System.exit(-1);
    }
}
