package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TopicsService {

    private List<Topic> topicList = new ArrayList<>(Arrays.asList(

            new Topic("_spring", "_Spring FrameWork", "_Spring Description", null, null, null, null, null, null, null, null, null, null, null),
            new Topic("spring", "Spring FrameWork", "Spring Description", null, null, null, null, null, null, null, null, null, null, null),
            new Topic("java", "Java FrameWork", "Java Description", null, null, null, null, null, null, null, null, null, null, null)

    ));

	public Topic read(Long id) {
		return null;
	}

	public Topic find(String id) {
		return null;
	}

	public Topic update(Long id, Topic topic) {
		return null;
	}

	public void delete(Long id) {
        }

	public Topic create(Topic topic) {
		return null;
	}
}