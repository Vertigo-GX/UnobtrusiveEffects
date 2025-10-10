package vertigo.unobtrusiveeffects.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vertigo.unobtrusiveeffects.UnobtrusiveEffectsClient;

@Mixin(InGameOverlayRenderer.class)
public abstract class InGameOverlayRendererMixin {

	@Inject(method = "renderFireOverlay", at = @At("HEAD"), cancellable = true)
	private static void renderFireOverlayInject(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Sprite sprite, CallbackInfo info) {
		if(UnobtrusiveEffectsClient.CONFIG.disableFireOverlay && MinecraftClient.getInstance().player.hasStatusEffect(
				StatusEffects.FIRE_RESISTANCE)) {
			info.cancel();
		}
	}

}