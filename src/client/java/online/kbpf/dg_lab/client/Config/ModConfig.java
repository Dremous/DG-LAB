package online.kbpf.dg_lab.client.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import online.kbpf.dg_lab.client.entity.NetworkAdapter;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.file.Path;

public class ModConfig {
    private boolean AutoStartWebSocketServer = true;
    private int RenderingPositionX = 20;
    private int RenderingPositionY = 20;
    private int port = 9999, serverPort = port;
    private String address, network;
    private boolean address2 = false, network2 = false, renderingMax = false;

    // 获取配置目录的绝对路径
    private static Path getConfigDir() {
        return FabricLoader.getInstance().getConfigDir().resolve("dg-lab");
    }

    // 获取配置文件的绝对路径
    private static File getConfigFile(String fileName) {
        return getConfigDir().resolve(fileName).toFile();
    }



    public ModConfig(boolean autoStartWebSocketServer, int x, int y) {
        AutoStartWebSocketServer = autoStartWebSocketServer;
        RenderingPositionX = x;
        RenderingPositionY = y;
        autoGetNetworkAddress();
        renderingMax = false;

    }

    public void autoGetNetworkAddress(){
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            if(localhost != null) {
                address = localhost.getHostAddress();
                address2 = true;
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localhost);
                if(networkInterface != null) {
                    network = networkInterface.getDisplayName();
                    network2 =true;
                }
            }
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = (port <0 || port > 65535) ? 9999 : port;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = (serverPort <0 || serverPort > 65535) ? 9999 : serverPort;
    }

    public String getAddress() {
        if(address2)
            return address;
        else return "error";
    }

    public String getNetwork() {
        if(network2)
            return network;
        else return "unknown";
    }

    public boolean isRenderingMax() {
        return renderingMax;
    }

    public void setRenderingMax(boolean renderingMax) {
        this.renderingMax = renderingMax;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setAddress(String address) {
        this.address = address;
        try {
            // 通过IP地址获取InetAddress对象
            InetAddress inetAddress = InetAddress.getByName(address);
            address2 = true;
            network2 = false;
            if(inetAddress != null) {
                // 通过InetAddress获取对应的网卡

                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
                if(networkInterface != null) {
                    network2 = true;
                    network = networkInterface.getDisplayName();
                }


            }

        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }

    public void savaFile(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = getConfigFile("ModConfig.json");
        try {
            if (!file.exists()) {
                getConfigDir().toFile().mkdirs();
                file.createNewFile();
            }
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(this, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ModConfig loadJson() {
        Gson gson = new Gson();
        File file = getConfigFile("ModConfig.json");
        if (!file.exists()) {
            return new ModConfig(true, 20, 20);
        }
        try (Reader reader = new FileReader(file)) {
            NetworkAdapter networkInterface = new NetworkAdapter();
            ModConfig modConfig = gson.fromJson(reader, ModConfig.class);
            if(!modConfig.address2 || (modConfig.network2&&networkInterface.getNetworkMap().size() == 1)) modConfig.autoGetNetworkAddress();
            else if(modConfig.network2){
                String address = networkInterface.NICGetaddress(modConfig.network);
                if(address != null)
                    modConfig.setAddress(address);
            }
            return modConfig;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getRenderingPositionX() {
        return RenderingPositionX;
    }

    public void setRenderingPositionX(int renderingPositionX) {
        RenderingPositionX = Math.max(0, renderingPositionX);
    }

    public int getRenderingPositionY() {
        return RenderingPositionY;
    }

    public void setRenderingPositionY(int renderingPositionY) {
        RenderingPositionY =Math.max(0, renderingPositionY);
    }

    public ModConfig() {
    }



    public boolean getAutoStartWebSocketServer() {
        return AutoStartWebSocketServer;
    }

    public void setAutoStartWebSocketServer(boolean autoStartWebSocketServer) {
        AutoStartWebSocketServer = autoStartWebSocketServer;
    }
}
