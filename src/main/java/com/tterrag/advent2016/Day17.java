package com.tterrag.advent2016;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.Value;
import lombok.experimental.NonFinal;

public class Day17 {

	enum Dir {
		U, D, L, R;

		public int offsetX(int x) {
			return this == L ? x - 1 : this == R ? x + 1 : x;
		}

		public int offsetY(int y) {
			return this == U ? y - 1 : this == D ? y + 1 : y;
		}
	}

	private static final String KEY = "awrkjxxr";
	private static final Set<Node> seen = new HashSet<>();

	@Value
	@RequiredArgsConstructor
	private static class Node {
		int x, y;
		String pathTo;

		transient @NonFinal String hash;
		transient Map<Dir, Boolean> open = new EnumMap<>(Dir.class);

		private static MessageDigest hasher;
		static {
			try {
				hasher = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}

		@SneakyThrows
		public Collection<Node> getValidNextNodes() {
			List<Node> ret = new ArrayList<>();
			for (Dir dir : Dir.values()) {
				if (isOpen(dir)) {
					ret.add(new Node(dir.offsetX(x), dir.offsetY(y), pathTo + dir));
				}
			}
			return ret;
		}

		public boolean isOpen(Dir d) {
			int newX = d.offsetX(x), newY = d.offsetY(y);
			if (newX < 0 || newY < 0 || newX > 3 || newY > 3) { 
				return false;
			}
			
			open.computeIfAbsent(d, dir -> {
				if (Node.this.hash == null) {
					byte[] data = hasher.digest((KEY + pathTo).getBytes());
					Node.this.hash  = String.format("%016x", ByteBuffer.wrap(data).getLong());
				}
				return hash.charAt(dir.ordinal()) >= 'b';
			});
			return open.get(d);
		}
	}

	public static void main(String[] args) {
		Node root = new Node(0, 0, "");

		Queue<Node> active = new ArrayDeque<>();
		active.add(root);
		
		int maxlen = -1;

		while (!active.isEmpty()) {
			Node cur = active.poll();
			for (Node n : cur.getValidNextNodes()) {
                if (seen.contains(n)) continue;
                seen.add(n);
                if (n.getX() == 3 && n.getY() == 3) {
                	if (maxlen < 0) {
                		System.out.println("Part 1: " + n.getPathTo());
                	}
                	maxlen = n.getPathTo().length();
				} else {
					active.add(n);
				}
			}
		}
		
		System.out.println("Part 2: " + maxlen);
	}
}
