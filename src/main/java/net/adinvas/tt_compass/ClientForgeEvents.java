package net.adinvas.tt_compass;



import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;

import net.minecraft.client.gui.GuiGraphics;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.HitResult;

import net.minecraft.world.phys.Vec3;
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


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if (event.phase == TickEvent.Phase.END){
            if (KeyBindings.TOGGLE_COMPASS.isDown()){
                KeyBindings.TOGGLE_COMPASS.consumeClick();
                Minecraft.getInstance().setScreen(new ConfigScreen(Minecraft.getInstance().screen));
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

                HitResult Raycast = mc.player.pick(0.005f,0,false);
                Vec3 endlocation = Raycast.getLocation();
                Vec3 startlocation = mc.player.getEyePosition(0f);
                Vec3 direction = endlocation
                        .subtract(startlocation)
                        .multiply(1,0,1);
                double yaw = Math.toDegrees(Math.atan2(direction.z, direction.x)) +90;
                if(!mc.player.isPassenger()){yaw = mc.player.getYRot()+180;}
                yaw = yaw %360;
                if (yaw<0){
                    yaw +=360;
                }

                String display = ""+(int)yaw;

                GuiGraphics gui = event.getGuiGraphics();
                int screen_width = mc.getWindow().getGuiScaledWidth();
                var font = mc.font;

                int x= screen_width /2;
                int y= CompassConfig.CLIENT.compassFromTop.get();






                float pxPerDeg = 2f;
                float displayWidth = CompassConfig.CLIENT.compassWidth.get();


                double scrollU = (yaw * pxPerDeg) - (displayWidth / 2f);
                if (CompassConfig.CLIENT.enableCompass.get()) {
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, (float) CompassConfig.CLIENT.compassOpacity.get()/1000);
                    gui.blit(CompassObject.SPRITES, x-((int)displayWidth/2), y, (int) scrollU, 0, (int) displayWidth, 32, CompassObject.TXT_WIDTH, CompassObject.TXT_HEIGHT);
                    int string_correction = 3;
                    if (yaw>10){
                        string_correction +=3;
                    }
                    if (yaw>100){
                        string_correction +=3;
                    }
                    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                    gui.drawString(font, display, x-string_correction, y+35, 0xFFFFFF);
                    RenderSystem.disableBlend();
                }
            }
    }
}
