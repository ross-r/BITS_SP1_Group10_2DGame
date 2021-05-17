package turd.game.platform;

	public class TheBigBang {

	public void Bang() {
		
		// (Length , Height)
		// Window size
		// 720
		// 1280
				
	
		// starting area, before first hurdle and rear wall
		new GroundTopMedium(-600, 100);
		new SingleGroundBottomFarRight(-700, 100);
		new GroundFillLong(-1100,200);
		new GroundFillLong(-1100,300);
		new GroundFillLong(-1100,400);
		
		new GroundFillLong(-1700, 100);
		new GroundFillMedium(-1600, 200);
		new GroundFillMedium(-1600, 300);
		new GroundFillMedium(-1600, 400);
		
		new GroundFillLong(-1800, 0);
		new GroundFillLong(-1800, -100);
		new GroundFillLong(-1800, -200);
		new GroundFillLong(-1800, -300);
		
		new SingleGroundBottomRight(-800, 0);
		new SingleGroundMidRight(-800, -100);
		new SingleGroundMidRight(-800, -200);
		new SingleGroundMidRight(-800, -300);
		
		new GroundTopEdgeLong(-1700, -400);
		new GroundTopLong(-100, 100);
		new GroundFillLong(-100,200);
		new GroundFillLong(-100,300);
		new GroundFillLong(-100,400);
		
		// first hurdle and ground below
		new GroundFillMedium(1000,100);
		
		new GroundFillSmall(1500,100);
		new GroundFillSmall(1700,100);
		
		new GroundFillLong(1000,200);
		new GroundFillLong(1000,300);
		new GroundFillLong(1000,400);
		
		new SingleGroundBottomFarLeft(900, 100);
		new SingleGroundMidMid(900, 200);
		new SingleGroundMidMid(900, 300);
		new SingleGroundMidMid(900, 400);
		new GroundTopEdgeLong(1000, 0);
		
		// floating platforms in starting area
		new FloatingPlatformSmall(650, -160);
		new FloatingPlatformSmall(200, -240);
		
		// first spikes encounter
		new HazardSpikes(2000, 100);
		new HazardSpikes(2100, 100);
		
		new SingleGroundBottomRightSpikesConnect(1900, 100);
		new GroundFillSmall(2000,200);
		
		new GroundTopEdgeLong(2200,0);
		new SingleGroundBottomLeftSpikesConnect(2200, 100);
	
		new GroundFillMedium(2300,100);
		new GroundFillMedium(2200,200);
		new GroundFillLong(2000,300);
		new GroundFillLong(2000,400);
		
		new GroundFillSmall(2800, 100);
		
		new GroundFillSmall(2700, 200);
		new GroundFillSmall(2900, 200);
		
		// bottomless pit and fill around it plus floating platform
		new SingleGroundMidMid(3000,100);
		new SingleGroundMidMid(3000,300);
		new SingleGroundMidMid(3000,400);
		
		new SingleGroundMidRight(3100,100);
		new SingleGroundMidRight(3100,200);
		new SingleGroundMidRight(3100,300);
		new SingleGroundMidRight(3100,400);
		
		new FloatingPlatformMedium(3450, -100);
		
		// other end of pit plus climb obstical
		
		new SingleGroundTopLeft(4300, 0);
		new SingleGroundBottomFarLeft(4400, 0);
		new SingleGroundTopLeft(4500, -100);
		new SingleGroundTopMid(4600,-100);
		new SingleGroundTopMid(4700,-100);
		new SingleGroundBottomFarLeft(4800, -100);
		new SingleGroundTopLeft(4900, -200);
		
		new SingleGroundBottomFarLeft(5000, -200);
		new SingleGroundTopLeft(5100, -300);
		
		// fill for climb obstical
		
		new GroundFillSmall(4400, 100);
		new GroundFillSmall(4400, 200);
		new GroundFillSmall(4400, 300);
		new GroundFillSmall(4400, 400);
		
		new SingleGroundMidLeft(4300, 100);
		new SingleGroundMidLeft(4300, 200);
		new SingleGroundMidLeft(4300, 300);
		new SingleGroundMidLeft(4300, 400);
		
		new GroundFillMedium(4500, 0);
		
		new GroundFillSmall(5000, 0);
		new GroundFillSmall(5200, 0);
		new GroundFillSmall(5400, 0);
		
		new GroundFillLong(4600, 100);
		new GroundFillLong(4600, 200);
		new GroundFillLong(4600, 300);
		new GroundFillLong(4600, 400);
		
		new GroundFillMedium(4900, -100);
		
		
		// floating platforms over bottomless pits before climd obstical
		new FloatingPlatformSmall(4550, -400);
		
	}
	
	
	
	
}
