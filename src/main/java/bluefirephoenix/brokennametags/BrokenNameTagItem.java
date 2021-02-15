package bluefirephoenix.brokennametags;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.text.TranslationException;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

public class BrokenNameTagItem extends Item {

    public String petName;

    public LocalDateTime deathTime;
    public EntityType<?> mobType;

    public BrokenNameTagItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        if (petName == null) {
            return Text.of("Broken Name Tag");
        }
        return Text.of(Formatting.ITALIC+petName);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (mobType != null && deathTime != null) {
            System.out.println(new TranslatableText(mobType.getTranslationKey()));
            //tooltip.add(Text.of(Formatting.GRAY + mobType.getName().asString()));
        }
    }
}
