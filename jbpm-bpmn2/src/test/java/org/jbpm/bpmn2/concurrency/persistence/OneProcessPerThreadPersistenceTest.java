package org.jbpm.bpmn2.concurrency.persistence;

import static org.jbpm.persistence.util.PersistenceUtil.cleanUp;
import static org.jbpm.persistence.util.PersistenceUtil.createEnvironment;
import static org.jbpm.persistence.util.PersistenceUtil.setupWithPoolingDataSource;

import java.util.HashMap;

import org.jbpm.bpmn2.concurrency.OneProcessPerThreadTest;
import org.jbpm.persistence.util.PersistenceUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.persistence.jpa.JPAKnowledgeService;
import org.kie.internal.runtime.StatefulKnowledgeSession;
import org.kie.api.runtime.Environment;

/**
 * Class to reproduce bug with multiple threads using persistence and each
 * configures its own entity manager.
 * 
 * This test takes time and resources, please only run it locally
 */
public class OneProcessPerThreadPersistenceTest extends OneProcessPerThreadTest {

    private static HashMap<String, Object> context;

    @Before
    public void setup() {
        context = setupWithPoolingDataSource(PersistenceUtil.JBPM_PERSISTENCE_UNIT_NAME);
    }

    @After
    public void tearDown() throws Exception {

        cleanUp(context);
    }

    protected StatefulKnowledgeSession createStatefulKnowledgeSession(KnowledgeBase kbase) {
        Environment env = createEnvironment(context);
        return JPAKnowledgeService.newStatefulKnowledgeSession(kbase, null, env);
    }

}
