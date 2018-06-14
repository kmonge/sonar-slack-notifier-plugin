package slacknotifier;

import static com.koant.sonar.slacknotifier.common.SlackNotifierProp.CONFIG;
import static com.koant.sonar.slacknotifier.common.SlackNotifierProp.ENABLED;
import static com.koant.sonar.slacknotifier.common.SlackNotifierProp.HOOK;
import static com.koant.sonar.slacknotifier.common.SlackNotifierProp.INCLUDE_BRANCH;
import static com.koant.sonar.slacknotifier.common.SlackNotifierProp.MESSAGE_TEMPLATE_ENABLED;
import static com.koant.sonar.slacknotifier.common.SlackNotifierProp.MESSAGE_TEMPLATE_VALUE;
import static com.koant.sonar.slacknotifier.common.SlackNotifierProp.USER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.koant.sonar.slacknotifier.SlackNotifierPlugin;
import com.koant.sonar.slacknotifier.extension.task.SlackPostProjectAnalysisTask;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.sonar.api.Plugin;
import org.sonar.api.PropertyType;
import org.sonar.api.config.PropertyDefinition;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SlackNotifierPluginTest {

    private SlackNotifierPlugin plugin = new SlackNotifierPlugin();

    @Test
    public void define_expectedExtensionsAdded() {

        Plugin.Context mockContext = mock(Plugin.Context.class);
        plugin.define(mockContext);
        ArgumentCaptor<List> arg = ArgumentCaptor.forClass(List.class);
        verify(mockContext, times(1)).addExtensions(arg.capture());

        List extensions = arg.getValue();
        assertEquals(8, extensions.size());
        assertEquals(HOOK.property(), ((PropertyDefinition) extensions.get(0)).key());
        assertEquals(USER.property(), ((PropertyDefinition) extensions.get(1)).key());
        assertEquals(ENABLED.property(), ((PropertyDefinition) extensions.get(2)).key());
        assertEquals(INCLUDE_BRANCH.property(), ((PropertyDefinition) extensions.get(3)).key());
        assertEquals(MESSAGE_TEMPLATE_ENABLED.property(), ((PropertyDefinition) extensions.get(4)).key());
        assertEquals(MESSAGE_TEMPLATE_VALUE.property(), ((PropertyDefinition) extensions.get(5)).key());
        assertEquals(PropertyType.TEXT, ((PropertyDefinition) extensions.get(5)).type());
        assertEquals(CONFIG.property(), ((PropertyDefinition) extensions.get(6)).key());
        assertEquals(SlackPostProjectAnalysisTask.class, extensions.get(7));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void define_noDupliacteIndexes() {

        Plugin.Context mockContext = mock(Plugin.Context.class);
        plugin.define(mockContext);
        ArgumentCaptor<List> arg = ArgumentCaptor.forClass(List.class);
        verify(mockContext, times(1)).addExtensions(arg.capture());

        List<Object> extensions = arg.getValue();

        Set<Integer> indexes =
            extensions.stream().filter(PropertyDefinition.class::isInstance).map(PropertyDefinition.class::cast)
                .map(PropertyDefinition::index).
                collect(Collectors.toSet());
        assertEquals(7, indexes.size());

    }

}
