package vertigo.unobtrusiveeffects.mixin.client;

import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import vertigo.unobtrusiveeffects.UnobtrusiveEffectsClient;

@Mixin(SpellParticle.class)
public abstract class SpellParticleMixin extends SpriteBillboardParticle {

	protected SpellParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
		super(clientWorld, d, e, f);
	}

	@Override
	public void render(VertexConsumer consumer, Camera camera, float delta) {
		if (!camera.isThirdPerson()) {
			float distance = UnobtrusiveEffectsClient.CONFIG.swirlCullingDistance;
			if (distance != 0f && camera.getPos().squaredDistanceTo(this.prevPosX, this.prevPosY, this.prevPosZ) <= distance) {
				return;
			}
		}
		super.render(consumer, camera, delta);
	}

}