package vertigo.unobtrusiveeffects.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vertigo.unobtrusiveeffects.UnobtrusiveEffectsClient;

@Mixin(ScreenEffectRenderer.class)
public abstract class ScreenEffectRendererMixin {

	@Inject(method = "renderFire", at = @At("HEAD"), cancellable = true)
	private static void renderFireInject(PoseStack stack, MultiBufferSource source, TextureAtlasSprite sprite, CallbackInfo info) {
		if(UnobtrusiveEffectsClient.CONFIG.disableFireOverlay && Minecraft.getInstance().player.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			info.cancel();
		}
	}

}