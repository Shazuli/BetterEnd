package ru.betterend.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import ru.bclib.util.MHelper;
import ru.betterend.entity.EndSlimeEntity;

public class EndSlimeEntityModel<T extends EndSlimeEntity> extends ListModel<T> {
	private final ModelPart innerCube;
	private final ModelPart rightEye;
	private final ModelPart leftEye;
	private final ModelPart mouth;
	private final ModelPart flower;
	private final ModelPart crop;

	public static LayerDefinition getShellOnlyTexturedModelData() {
		return getTexturedModelData(true);
	}
	public static LayerDefinition getCompleteTexturedModelData() {
		return getTexturedModelData(false);
	}

	private static LayerDefinition getTexturedModelData(boolean onlyShell) {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();

		//this.innerCube = new ModelPart(this, 0, 16);
		if (onlyShell) {
			modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create()
							.texOffs(0, 0)
							.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F),
					PartPose.ZERO);
			/* this.innerCube.texOffs(0, 0);
			this.innerCube.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);*/
		} else {
			modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create()
							.texOffs(0, 16)
							.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F),
					PartPose.ZERO);
			//this.innerCube.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);

			modelPartData.addOrReplaceChild(PartNames.RIGHT_EYE, CubeListBuilder.create()
							.texOffs(32, 0)
							.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F),
					PartPose.ZERO);
			//this.rightEye = new ModelPart(this, 32, 0);
			//this.rightEye.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);

			modelPartData.addOrReplaceChild(PartNames.LEFT_EYE, CubeListBuilder.create()
							.texOffs(32, 4)
							.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F),
					PartPose.ZERO);
			//this.leftEye = new ModelPart(this, 32, 4);
			//this.leftEye.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);

			modelPartData.addOrReplaceChild(PartNames.MOUTH, CubeListBuilder.create()
							.texOffs(32, 8)
							.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F),
					PartPose.ZERO);
			//this.mouth = new ModelPart(this, 32, 8);
			//this.mouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);

			PartDefinition flowerPart = modelPartData.addOrReplaceChild("flower", CubeListBuilder.create(), PartPose.ZERO);
			PartDefinition cropPart = modelPartData.addOrReplaceChild("crop", CubeListBuilder.create(), PartPose.ZERO);

			for (int i = 0; i < 6; i++) {
				final PartDefinition parent = i<4?flowerPart:cropPart;
				final float rot = MHelper.degreesToRadians( i<4 ? (i * 45F) : ((i-4) * 90F + 45F) );

				PartDefinition petalRotPart = parent.addOrReplaceChild("petalRot_"+i,
						CubeListBuilder.create(),
						PartPose.offsetAndRotation(0,0,0,0,rot, 0));


				petalRotPart.addOrReplaceChild("petal_"+i,
						CubeListBuilder.create()
								.texOffs(40, 0)
								.addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F),
						PartPose.offset(-4, 8, 0));
			}
			/* this.flower = new ModelPart(this);
			for (int i = 0; i < 4; i++) {
				ModelPart petalRot = new ModelPart(this);
				petalRot.yRot = MHelper.degreesToRadians(i * 45F);

				ModelPart petal = new ModelPart(this, 40, 0);
				petal.setPos(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F);

				this.flower.addChild(petalRot);
				petalRot.addChild(petal);
			}
			this.crop = new ModelPart(this);

			for (int i = 0; i < 2; i++) {
				ModelPart petalRot = new ModelPart(this);
				petalRot.yRot = MHelper.degreesToRadians(i * 90F + 45F);

				ModelPart petal = new ModelPart(this, 40, 0);
				petal.setPos(-4, 8, 0);
				petal.addBox(0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 0.0F, 0.0F);

				this.crop.addChild(petalRot);
				petalRot.addChild(petal);
			}
			 */
		}

		return LayerDefinition.create(modelData, 64, 32);
	}

	public EndSlimeEntityModel(ModelPart modelPart, boolean onlyShell) {
		super(RenderType::entityCutout);

		innerCube = modelPart.getChild(PartNames.BODY);
		if (!onlyShell) {
			rightEye = modelPart.getChild(PartNames.RIGHT_EYE);
			leftEye = modelPart.getChild(PartNames.LEFT_EYE);
			mouth = modelPart.getChild(PartNames.MOUTH);
			flower = modelPart.getChild("flower");
			crop = modelPart.getChild("crop");
		} else {
			rightEye = null;
			leftEye = null;
			mouth = null;
			flower = null;
			crop = null;
		}
	}

	@Override
	public void setupAnim(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
			float headPitch) {
	}

	public void renderFlower(PoseStack matrices, VertexConsumer vertices, int light, int overlay) {
		flower.render(matrices, vertices, light, overlay);
	}

	public void renderCrop(PoseStack matrices, VertexConsumer vertices, int light, int overlay) {
		crop.render(matrices, vertices, light, overlay);
	}

	private boolean isOnlyShell(){
		return rightEye==null;
	}

	@Override
	public Iterable<ModelPart> parts() {
		if (isOnlyShell()) {
			return ImmutableList.of(this.innerCube);
		} else {
			return ImmutableList.of(this.innerCube, this.rightEye, this.leftEye, this.mouth);
		}
	}
}
