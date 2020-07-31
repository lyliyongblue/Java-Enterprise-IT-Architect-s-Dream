package com.liyong.webflux.demo.reactor.demo2;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FutureDemo {
	public void ss() {
		CompletableFuture<List<String>> ids = ifhIds();

		CompletableFuture<List<String>> result = ids.thenComposeAsync(list -> {
			Stream<CompletableFuture<String>> zip = list.stream().map(id -> {
				CompletableFuture<String> nameTask = ifhName(id);
				CompletableFuture<Integer> statTask = ifhStat(id);

				return nameTask.thenCombineAsync(statTask, (name, stat) -> "Name " + name + " has stats " + stat);
			});
			List<CompletableFuture<String>> combinationList = zip.collect(Collectors.toList());
			@SuppressWarnings("unchecked")
			CompletableFuture<String>[] combinationArray = combinationList.toArray(new CompletableFuture[combinationList.size()]);

			CompletableFuture<Void> allDone = CompletableFuture.allOf(combinationArray);
			return allDone.thenApply(v -> combinationList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
		});

		List<String> results = result.join();
	}

	private CompletableFuture<Integer> ifhStat(String i) {
		// TODO Auto-generated method stub
		return null;
	}

	private CompletableFuture<String> ifhName(String i) {
		// TODO Auto-generated method stub
		return null;
	}

	private CompletableFuture<List<String>> ifhIds() {
		return null;
	}
}
