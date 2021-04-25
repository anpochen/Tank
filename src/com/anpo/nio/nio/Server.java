package com.anpo.nio.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost",8888));
        serverSocketChannel.configureBlocking(false);   //设置非阻塞

        System.out.println("server started, linstening on : "+ serverSocketChannel.getLocalAddress());

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);   //设置接受的类型

        while (true){    //应该根据业务加上自己的判断变量
            selector.select();
            Set<SelectionKey> keySet = selector.keys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();          //需要先移除掉，免得多线程重复消费
                handle(key);                //处理
            }
        }
    }

    private static void handle(SelectionKey key) throws IOException {
        if (key.isAcceptable()){
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.register(key.selector(),SelectionKey.OP_READ);

        }else if (key.isReadable()){
            SocketChannel socketChannel = null;
            try {
                socketChannel = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                byteBuffer.clear();

                int length = socketChannel.read(byteBuffer);

                if (length != -1){
                    System.out.println(new String(byteBuffer.array(),0,length));
                }

                ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
                socketChannel.write(bufferToWrite);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
