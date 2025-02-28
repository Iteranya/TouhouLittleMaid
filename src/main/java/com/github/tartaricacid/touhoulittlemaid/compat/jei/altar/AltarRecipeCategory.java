package com.github.tartaricacid.touhoulittlemaid.compat.jei.altar;

import com.github.tartaricacid.touhoulittlemaid.TouhouLittleMaid;
import com.mojang.blaze3d.matrix.MatrixStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class AltarRecipeCategory implements IRecipeCategory<AltarRecipeWrapper> {
    public static final ResourceLocation UID = new ResourceLocation(TouhouLittleMaid.MOD_ID, "altar");
    private static final ResourceLocation ALTAR_ICON = new ResourceLocation(TouhouLittleMaid.MOD_ID, "textures/gui/altar_icon.png");
    private static final ResourceLocation POWER_ICON = new ResourceLocation(TouhouLittleMaid.MOD_ID, "textures/entity/power_point.png");
    private final IDrawableStatic bgDraw;
    private final IDrawable slotDraw;
    private final IDrawableStatic altarDraw;
    private final IDrawableStatic powerDraw;

    public AltarRecipeCategory(IGuiHelper guiHelper) {
        this.bgDraw = guiHelper.createBlankDrawable(160, 125);
        this.slotDraw = guiHelper.getSlotDrawable();
        this.altarDraw = guiHelper.drawableBuilder(ALTAR_ICON, 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.powerDraw = guiHelper.drawableBuilder(POWER_ICON, 32, 0, 16, 16).setTextureSize(64, 64).build();
    }

    @Override
    public void setIngredients(AltarRecipeWrapper recipe, IIngredients ingredients) {
        ingredients.setInputLists(VanillaTypes.ITEM, recipe.getInputs());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }

    @Override
    public void draw(AltarRecipeWrapper recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        int darkGray = 0x555555;
        FontRenderer font = Minecraft.getInstance().font;
        String result = I18n.get("jei.touhou_little_maid.altar_craft.result", I18n.get(recipe.getLangKey()));

        matrixStack.pushPose();
        matrixStack.scale(0.8f, 0.8f, 0.8f);
        powerDraw.draw(matrixStack, 90, 50);
        matrixStack.popPose();

        font.draw(matrixStack, String.format("×%.2f", recipe.getPowerCost()), 65, 55, darkGray);
        font.draw(matrixStack, result, (bgDraw.getWidth() - font.width(result)) / 2.0f, 85, darkGray);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, AltarRecipeWrapper recipe, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        guiItemStacks.init(0, true, 40, 35);
        guiItemStacks.init(1, true, 40, 55);
        guiItemStacks.init(2, true, 60, 15);
        guiItemStacks.init(3, true, 80, 15);
        guiItemStacks.init(4, true, 100, 35);
        guiItemStacks.init(5, true, 100, 55);
        guiItemStacks.init(6, false, 140, 5);
        guiItemStacks.setBackground(0, slotDraw);
        guiItemStacks.setBackground(1, slotDraw);
        guiItemStacks.setBackground(2, slotDraw);
        guiItemStacks.setBackground(3, slotDraw);
        guiItemStacks.setBackground(4, slotDraw);
        guiItemStacks.setBackground(5, slotDraw);
        guiItemStacks.setBackground(6, slotDraw);
        guiItemStacks.set(ingredients);
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public Class<? extends AltarRecipeWrapper> getRecipeClass() {
        return AltarRecipeWrapper.class;
    }

    @Override
    public String getTitle() {
        return I18n.get("jei.touhou_little_maid.altar_craft.title");
    }

    @Override
    public IDrawable getBackground() {
        return bgDraw;
    }

    @Override
    public IDrawable getIcon() {
        return altarDraw;
    }
}
