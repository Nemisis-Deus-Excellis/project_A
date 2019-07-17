package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DemoModelsService {

    private List<DemoModel> DemoModelList = new ArrayList<>(Arrays.asList(

            new DemoModel("_spring", "_Spring FrameWork", "_Spring Description", null, null, null, null, null, null, null, null, null, null, null),
            new DemoModel("spring", "Spring FrameWork", "Spring Description", null, null, null, null, null, null, null, null, null, null, null),
            new DemoModel("java", "Java FrameWork", "Java Description", null, null, null, null, null, null, null, null, null, null, null)

    ));

	public DemoModel read(Long id) {
		return null;
	}

	public DemoModel find(String id) {
		return null;
	}

	public DemoModel update(Long id, DemoModel DemoModel) {
		return null;
	}

	public void delete(Long id) {
        }

	public DemoModel create(DemoModel DemoModel) {
		return null;
	}
}