package net.adinvas.mnsaddition;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "mnsaddition", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    public static void register(){
        MnsAddition.LOGGER.info("Yaw overlay registered!");
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
            public static final ResourceLocation SPRITES = ResourceLocation.fromNamespaceAndPath("mnsaddition","textures/gui/compass_sprite_x8.png");
            public static final int FRAME_WIDTH = 32;
            public static final int FRAME_HEIGHT = 32;
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

                String display = ""+Math.floor(yaw*10)/10;

                GuiGraphics gui = event.getGuiGraphics();
                PoseStack pose = gui.pose();
                int screen_width = mc.getWindow().getGuiScaledWidth();
                int screen_height = mc.getWindow().getGuiScaledHeight();
                var font = mc.font;

                int x= screen_width /2;
                int y= screen_height -49;

                int frameindex = (int) (8-Math.floor((yaw+22.5f)/45f));
                int u = frameindex * CompassObject.FRAME_WIDTH;
                int v =0;


                if (COMPASS_OVERLAY) {
                    pose.pushPose();
                    pose.scale(0.5f,0.5f,1f);
                    RenderSystem.setShaderTexture(0,CompassObject.SPRITES);
                    gui.blit(CompassObject.SPRITES,x*2-16,y*2,u,v,CompassObject.FRAME_WIDTH,CompassObject.FRAME_HEIGHT,256,32);
                    pose.popPose();
                    gui.drawString(font, display, x-10, y-5, 0xFFFFFF);
                }
            }
    }
}
