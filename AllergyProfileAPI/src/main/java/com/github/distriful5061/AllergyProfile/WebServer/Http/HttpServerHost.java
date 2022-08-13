package com.github.distriful5061.AllergyProfile.WebServer.Http;

import com.github.distriful5061.AllergyProfile.Handlers.WebHandlers.AbstractBaseHandler;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpMethod;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpRequest;
import com.github.distriful5061.AllergyProfile.WebServer.Http.Connections.HttpResponse;

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
        if (handlerList.containsKey(path + "|" + baseHandler.getSupportedMethod())) {
            handlerList.replace(path, baseHandler);
            return;
        }
        handlerList.put(path, baseHandler);
    }

    /**
     * AbstractBaseHandlerベースのハンドラーをリストから削除するメソッド
     *
     * @param path そのハンドラーが割り当てられているpath
     * @return そのabstractBaseHandlerのインスタンス
     * @see AbstractBaseHandler
     */
    public static AbstractBaseHandler deleteHandler(String path) {
        AbstractBaseHandler tmp = null;
        if (handlerList.containsKey(path)) {
            tmp = handlerList.get(path);
            handlerList.remove(path);
        }
        return tmp;
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
        } catch (IOException ignored) {
            System.out.println("System downed");// LogUtilsに置き換え予定
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

                            if (requestMethod == HttpMethod.GET) {// GET以外のメソッドはこの後実装します
                                if (handlerList.containsKey(path + "|" + HttpMethod.GET)) {
                                    flag = false;

                                    AbstractBaseHandler abstractBaseHandler = handlerList.get(path);

                                    try {
                                        abstractBaseHandler.run(inputStream, outputStream);
                                    } catch (Throwable e) {
                                        throw new IOException(e.getMessage());
                                    }
                                }
                            } else if (requestMethod == HttpMethod.POST) {
                                throwSimpleCode(outputStream, HttpStatusCode.BAD_REQUEST);
                            } else if (requestMethod == HttpMethod.PUT) {
                                throwSimpleCode(outputStream, HttpStatusCode.BAD_REQUEST);
                            } else if (requestMethod == HttpMethod.DELETE) {
                                throwSimpleCode(outputStream, HttpStatusCode.BAD_REQUEST);
                            }

                            if (flag) {
                                throwSimpleCode(outputStream, HttpStatusCode.NOT_FOUND);
                            }

                        } catch (IOException ignored) {
                            try {
                                throwSimpleCode(outputStream, HttpStatusCode.INTERNAL_SERVER_ERROR);
                            } catch (IOException ignored1) {}
                        } finally {
                            try {
                                socket.close();
                                inputStream.close();
                                outputStream.close();
                            } catch (IOException ignored1) {}
                        }
                    };
                    new Thread(socketProcessor).start();

                } catch (IOException ignored) {
                }
            }
        } catch (Throwable e) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }

        System.out.println("oh no system are shutdown ed");
        System.exit(-1);
    }
}
