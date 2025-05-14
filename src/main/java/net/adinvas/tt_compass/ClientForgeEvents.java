package net.adinvas.tt_compass;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "tt_compass", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {
    public static void register(){
        TTCompass.LOGGER.info("Yaw overlay registered!");
    }

    public static boolean COMPASS_OVERLAY = true;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if (event.phase == TickEvent.Phase.END){
            if (KeyBindings.TOGGLE_COMPASS.isDown()){
                KeyBindings.TOGGLE_COMPASS.setDown(false);
                KeyBindings.TOGGLE_COMPASS.consumeClick();
                COMPASS_OVERLAY = !COMPASS_OVERLAY;
            }
        }
    }

        public static class CompassObject {
            public static final ResourceLocation SPRITES = new ResourceLocation("mnsaddition","textures/gui/line_compass.png");
            //public static final  ResourceLocation ALPHA_MASK = new ResourceLocation("mnsaddition","textures/gui/compass_mask.png");
            public static final int TXT_WIDTH = 720;
            public static final int TXT_HEIGHT = 32;
        }




        @SubscribeEvent
        public static void onRenderOverlay(RenderGuiOverlayEvent.Post event){
            Minecraft mc = Minecraft.getInstance();

            if (mc.player !=null){
                float yaw = mc.player.getYRot() +180;

                yaw = yaw %360;
                if (yaw<0){
                    yaw +=360;
                }

                String display = ""+(int)yaw;

                GuiGraphics gui = event.getGuiGraphics();
                int screen_width = mc.getWindow().getGuiScaledWidth();
                var font = mc.font;

                int x= screen_width /2;
                int y= 15 ;






                float pxPerDeg = 2f;
                float displayWidth = 180f;


                float scrollU = (yaw * pxPerDeg) - (displayWidth / 2f);
                if (COMPASS_OVERLAY) {
                    gui.blit(CompassObject.SPRITES, x-90, y, (int) scrollU, 0, (int) displayWidth, 32, CompassObject.TXT_WIDTH, CompassObject.TXT_HEIGHT);
                    int string_correction = 3;
                    if (yaw>10){
                        string_correction +=3;
                    }
                    if (yaw>100){
                        string_correction +=3;
                    }
                    gui.drawString(font, display, x-string_correction, y+35, 0xFFFFFF);
                }
            }
    }
}
