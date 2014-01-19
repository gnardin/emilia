package emilia;

import org.junit.Test;

import emilia.entity.event.EventEntity;
import emilia.entity.event.EventEntity.EventType;
import emilia.modules.classifier.EventClassifier;
import emilia.modules.classifier.EventClassifierListener;

public class EventClassifierTest extends EventClassifierListener {

	public EventClassifierTest() {
		super(1);
	}

	@Test
	public void test() {
		EventClassifier classifier = new EventClassifier();

		classifier.registerCallback(EventType.ACTION, this);

		EventEntity event = new EventEntity(120, 1, 2, EventType.ACTION, null);
		classifier.input(event);
	}

	@Override
	public void receive(EventEntity event) {
		System.out.println(event.getId());
	}
}