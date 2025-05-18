package net.adinvas.tt_compass;


import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;



public class ConfigScreen extends Screen {
    private final Screen parent;

    protected ConfigScreen(Screen parent){
        super(Component.literal("Compass Config"));
        this.parent = parent;
    }

    @Override
    public void onClose(){
        this.minecraft.setScreen(parent);
    }

    @Override
    public void renderBackground(GuiGraphics graphics) {
        // Draw a semi-transparent black rectangle over the whole screen
        graphics.fill(0, 0, this.width, this.height, 0xCC000000);
    }


    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics); // <== Draw the semi-transparent background first
        super.render(graphics, mouseX, mouseY, partialTick);

        // Your config UI elements render next
    }


    private EditBox compassWidthBox;
    private EditBox compassHeightBox;
    @Override
    protected void init(){
        this.addRenderableWidget(Button.builder(Component.literal("Toggle Compass"),btn ->{
            boolean current = CompassConfig.CLIENT.enableCompass.get();
            CompassConfig.CLIENT.enableCompass.set(!current);
        }).bounds(this.width/2-100,40,200,20).build());

        this.addRenderableWidget(new StringWidget(
                this.width/2 -145,60,200,20,
                Component.literal("Compass Width(1-256)"),this.font
        ));
        compassWidthBox = new EditBox(this.font,this.width/2+60,60,40,20,Component.literal("Compass Width"));
        compassWidthBox.setValue(String.valueOf(CompassConfig.CLIENT.compassWidth.get()));
        this.addRenderableWidget(compassWidthBox);

        this.addRenderableWidget(new StringWidget(
                this.width/2 -130,80,200,20,
                Component.literal("Compass top offset(0-256)"),this.font
        ));
        compassHeightBox = new EditBox(this.font,this.width/2+60,80,40,20,Component.literal("Compass from the top"));
        compassHeightBox.setValue(String.valueOf(CompassConfig.CLIENT.compassFromTop.get()));
        this.addRenderableWidget(compassHeightBox);


        this.addRenderableWidget(Button.builder(Component.literal("Save"), btn -> {
            try {
                int widthval = Integer.parseInt(compassWidthBox.getValue());
                if (widthval >= 0 && widthval <= 256) {
                    CompassConfig.CLIENT.compassWidth.set(widthval);
                }
            } catch (NumberFormatException e) {
            }
            try {
                int heightval = Integer.parseInt(compassHeightBox.getValue());
                if (heightval >= 0 && heightval <= 256) {
                    CompassConfig.CLIENT.compassFromTop.set(heightval);
                }
            } catch (NumberFormatException e) {
            }
            this.onClose();
    }).bounds(this.width / 2 - 100, 140, 200, 20).build());
}}
