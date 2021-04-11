package turd.game.audio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;
import static org.lwjgl.BufferUtils.*;

// Sound package utilities.
public class AudioUtils {

	// Load file to a Byte Buffer.
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException {

		ByteBuffer bbBuffer;

		Path path = Paths.get(resource);

		if (Files.isReadable(path)) {
			try (SeekableByteChannel fc = Files.newByteChannel(path)) {
				bbBuffer = BufferUtils.createByteBuffer((int) fc.size() + 1);
				while (fc.read(bbBuffer) != -1);
			}
		} else {
			try (InputStream source = AudioUtils.class.getResourceAsStream(resource);
					ReadableByteChannel rbc = Channels.newChannel(source)) {

				bbBuffer = createByteBuffer(bufferSize);

				while (true) {
					int bytes = rbc.read(bbBuffer);
					if (bytes == -1) {
						break;
					}
					if (bbBuffer.remaining() == 0) {
						bbBuffer = resizeBuffer(bbBuffer, bbBuffer.capacity() * 2);
					}
				}
			}
		}

		bbBuffer.flip();
		return bbBuffer;
	}
<<<<<<< HEAD

	// Resize a byte buffer
=======
	
	//Resize a byte buffer
>>>>>>> 70f92bb01329801f90ff8fc9972f5767101cb8c7
	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity) {

		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);

		buffer.flip();

		newBuffer.put(buffer);
		return newBuffer;

	}
}
