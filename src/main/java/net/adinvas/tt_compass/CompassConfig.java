package net.adinvas.tt_compass;

import net.minecraftforge.common.ForgeConfigSpec;

public class CompassConfig {
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final ClientConfig CLIENT;

    static {
        var builder = new ForgeConfigSpec.Builder();
        CLIENT = new ClientConfig(builder);
        CLIENT_SPEC = builder.build();
    }


    public static class ClientConfig{
        public final ForgeConfigSpec.BooleanValue enableCompass;
        public final ForgeConfigSpec.IntValue compassWidth;
        public final ForgeConfigSpec.IntValue compassFromTop;
        public final ForgeConfigSpec.IntValue compassOpacity;

        public ClientConfig(ForgeConfigSpec.Builder builder){
            builder.push("general");
            enableCompass = builder
                    .comment("Eables The printing of the Compass")
                    .define("enableCompass",true);

            compassWidth = builder
                    .comment("Widhth of the Compass")
                    .defineInRange("compassWidth",180,0,256);

            compassFromTop = builder
                    .comment("Sets Compass height from the top of the screen")
                    .defineInRange("compassFromTop",15,0,256);
            compassOpacity = builder
                    .comment("Sets Compass Opacity")
                    .defineInRange("compassOpacity",100,0,100);


            builder.pop();
        }
    }
}
