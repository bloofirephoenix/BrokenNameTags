package bluefirephoenix.brokennametags;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BrokenNameTagItem extends Item {

    public BrokenNameTagItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.getTag() != null) {
            String deathMessage = stack.getTag().getCompound("data").getString("deathMessage");
            tooltip.add(Text.of(Formatting.GRAY+deathMessage));
        }
    }
}
