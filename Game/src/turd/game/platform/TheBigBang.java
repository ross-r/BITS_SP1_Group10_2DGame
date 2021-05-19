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
		new GroundFillLong(-1100, 200);
		new GroundFillLong(-1100, 300);
		new GroundFillLong(-1100, 400);

		new GroundFillLong(-1700, 100);
		new GroundFillMedium(-1600, 200);
		new GroundFillMedium(-1600, 300);
		new GroundFillMedium(-1600, 400);

		new GroundFillLong(-1800, 0);

		new GroundFillLong(-1800, -100);
		new GroundFillLong(-1800, -200);
		new GroundFillLong(-1800, -300);
		new GroundFillLong(-1800, -400);
		new GroundFillLong(-1800, -500);
		new GroundFillLong(-1800, -600);
		new GroundFillLong(-1800, -700);
		new GroundFillLong(-1800, -800);
		new GroundFillLong(-1800, -900);
		new GroundFillLong(-1800, -1000);
		new GroundFillLong(-1800, -1100);
		new GroundFillLong(-1800, -1200);
		new GroundFillLong(-1800, -1300);
		new GroundFillLong(-1800, -1400);
		new GroundFillLong(-1800, -1500);

		new SingleGroundBottomRight(-800, 0);

		new SingleGroundMidRight(-800, -100);
		new SingleGroundMidRight(-800, -200);
		new SingleGroundMidRight(-800, -300);
		new SingleGroundMidRight(-800, -400);
		new SingleGroundMidRight(-800, -500);
		new SingleGroundMidRight(-800, -600);
		new SingleGroundMidRight(-800, -700);
		new SingleGroundMidRight(-800, -800);
		new SingleGroundMidRight(-800, -900);
		new SingleGroundMidRight(-800, -1000);
		new SingleGroundMidRight(-800, -1100);
		new SingleGroundMidRight(-800, -1200);
		new SingleGroundMidRight(-800, -1300);
		new SingleGroundMidRight(-800, -1400);
		new SingleGroundMidRight(-800, -1500);

		new GroundTopEdgeLong(-1700, -1600);

		new GroundTopLong(-100, 100);
		new GroundFillLong(-100, 200);
		new GroundFillLong(-100, 300);
		new GroundFillLong(-100, 400);

		// first hurdle and ground below
		new GroundFillMedium(1000, 100);

		new GroundFillSmall(1500, 100);
		new GroundFillSmall(1700, 100);

		new GroundFillLong(1000, 200);
		new GroundFillLong(1000, 300);
		new GroundFillLong(1000, 400);

		new SingleGroundBottomFarLeft(900, 100);
		new SingleGroundMidMid(900, 200);
		new SingleGroundMidMid(900, 300);
		new SingleGroundMidMid(900, 400);
		new GroundTopEdgeLong(1000, 0);

		// floating platforms in starting area
		new FloatingPlatformSmall(600, -350);
		new FloatingPlatformMedium(1400, -420);

		// first spikes encounter
		new HazardSpikes(2000, 100);
		new HazardSpikes(2100, 100);

		new SingleGroundBottomRightSpikesConnect(1900, 100);
		new GroundFillSmall(2000, 200);

		new GroundTopEdgeLong(2200, 0);
		new SingleGroundBottomLeftSpikesConnect(2200, 100);

		new GroundFillMedium(2300, 100);
		new GroundFillMedium(2200, 200);
		new GroundFillLong(2000, 300);
		new GroundFillLong(2000, 400);

		new GroundFillSmall(2800, 100);

		new GroundFillSmall(2700, 200);
		new GroundFillSmall(2900, 200);

		// bottomless pit and fill around it plus floating platform
		new SingleGroundMidMid(3000, 100);
		new SingleGroundMidMid(3000, 300);
		new SingleGroundMidMid(3000, 400);

		new SingleGroundMidRight(3100, 100);
		new SingleGroundMidRight(3100, 200);
		new SingleGroundMidRight(3100, 300);
		new SingleGroundMidRight(3100, 400);

		new FloatingPlatformMedium(3450, -100);

		// floating platforms over bottomless pits before climb obstical
		new FloatingPlatformSmall(4250, -600);
		new FloatingPlatformSmall(3700, -750);
		new FloatingPlatformSmall(3200, -1100); // make enclosed later ?

		// other end of pit plus climb obstical
		// step1
		new SingleGroundTopLeft(4300, 0);
		new SingleGroundBottomFarLeft(4400, 0);
		// step2
		new SingleGroundTopLeft(4500, -100);
		new SingleGroundTopMid(4600, -100);
		new SingleGroundTopMid(4700, -100);
		new SingleGroundBottomFarLeft(4800, -100);
		// step3
		new SingleGroundTopLeft(4900, -200);
		new SingleGroundBottomFarLeft(5000, -200);
		// step4
		new SingleGroundTopLeft(5100, -300);
		new SingleGroundBottomFarLeft(5200, -300);
		// step5(double step up)
		new SingleGroundBottomLeft(5300, -400);
		new SingleGroundTopLeft(5300, -500);

		// top of pyramid obstical
		new GroundTopSmall(5400, -500);
		new SingleGroundTopRight(5600, -500);
		new HazardSpikes(5700, -500);

		// raised 1
		new SingleGroundTopLeft(5800, -600);
		new SingleGroundTopRight(5900, -600);
		new SingleGroundMidLeft(5800, -500);
		new SingleGroundMidRight(5900, -500);
		new HazardSpikes(6000, -500);

		// raised 2
		new SingleGroundMidLeft(6100, -500);
		new SingleGroundMidRight(6200, -500);
		new SingleGroundMidLeft(6100, -600);
		new SingleGroundMidRight(6200, -600);
		new SingleGroundTopLeft(6100, -700);
		new SingleGroundTopRight(6200, -700);
		new HazardSpikes(6300, -500);

		// raised 3
		new SingleGroundMidLeft(6400, -500);
		new SingleGroundMidRight(6500, -500);
		new SingleGroundMidLeft(6400, -600);
		new SingleGroundMidRight(6500, -600);
		new SingleGroundMidLeft(6400, -700);
		new SingleGroundMidRight(6500, -700);
		new SingleGroundTopLeft(6400, -800);
		new SingleGroundTopRight(6500, -800);
		new HazardSpikes(6600, -500);
		new GroundFillSmall(6400, -400);

		// raised 2

		new SingleGroundMidLeft(6700, -500);
		new SingleGroundMidRight(6800, -500);
		new SingleGroundMidLeft(6700, -600);
		new SingleGroundMidRight(6800, -600);
		new SingleGroundTopLeft(6700, -700);
		new SingleGroundTopRight(6800, -700);
		new HazardSpikes(6900, -500);

		// raised 1
		new SingleGroundMidLeft(7000, -500);
		new SingleGroundMidRight(7100, -500);
		new SingleGroundTopLeft(7000, -600);
		new SingleGroundTopRight(7100, -600);
		new HazardSpikes(7200, -500);

		new SingleGroundTopLeft(7300, -500);
		new GroundTopMedium(7400, -500);

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

		new GroundFillLong(5400, -400);
		new GroundFillLong(5300, -300);
		new GroundFillLong(5100, -200);
		new GroundFillLong(5400, -100);
		new GroundFillLong(5600, 0);
		new GroundFillLong(5600, 100);

		new GroundFillLong(6600, -400);
		new GroundFillLong(6300, -300);
		new GroundFillLong(6100, -200);

		// over-hang after spikes step ups
		new SingleGroundPlatformEdgeConnectLeft(7900, -500);
		new SingleGroundPlatformMid(8000, -500);
		new SingleGroundPlatformMid(8100, -500);
		new SingleGroundPlatformMid(8200, -500);
		new SingleGroundPlatformMid(8300, -500);
		new SingleGroundPlatformRight(8400, -500);
	
		new SingleGroundWallTopRight(7800, -400);
		new SingleGroundMidRight(7800, -300);
		new SingleGroundMidRight(7800, -200);
		new SingleGroundMidRight(7800, -100);
	
		// over-hang fill
		new GroundFillSmall(7600, -400);
		new GroundFillMedium(7300, -300);
		new GroundFillLong(6800, -200);
		new GroundFillLong(6800, -100);
		new GroundFillSmall(7700, 0);
		
		new GroundFillMedium(7500, 0);
		new GroundFillMedium(7000, 0);
		
		new GroundFillLong(7000, 100);
		new GroundFillMedium(6500, 100);
		new GroundFillLong(7000, 200);
		new GroundFillMedium(6500, 200);
		new GroundFillLong(7000, 300);
		new GroundFillMedium(6500, 300);
		new GroundFillLong(7000, 400);
		new GroundFillMedium(6500, 400);
	
		new GroundFillMedium(8000, 100);
		new GroundFillMedium(8000, 200);
		new GroundFillMedium(8000, 300);
		new GroundFillMedium(8000, 400);
		
		// floating platforms above over-hang and over the pillar jummping obsticals
		new FloatingPlatformMedium(7600, -900);
		new FloatingPlatformSmall(8600, -800);
		new FloatingPlatformSmall(10000, -700);
		new FloatingPlatformSmall(11000, -800);
		
		
		// over-hang platform bellow
		new GroundTopMedium(8000, 0);
		new SingleGroundBottomFarRight(7900, 0);
		new SingleGroundTopRight(8700, 0);
		new SingleGroundMidRight(8700, 100);
		new SingleGroundMidRight(8700, 200);
		new SingleGroundMidRight(8700, 300);
		
		new GroundTopSmall(8500, 0);
		new GroundFillSmall(8500, 100);
		new GroundFillSmall(8500, 200);
		new GroundFillSmall(8500, 300);
		
		
		
		// pillar jummping obsticals after over-hang with fill
		
		// pillar1
		new SingleGroundTopLeft(9700, 0);
		new GroundTopSmall(9800, 0);
		new SingleGroundTopRight(10000, 0);
		
		new SingleGroundMidLeft(9700, 100);
		new GroundFillSmall(9800, 100);
		new SingleGroundMidRight(10000, 100);
		new SingleGroundMidLeft(9700, 200);
		new GroundFillSmall(9800, 200);
		new SingleGroundMidRight(10000, 200);
		new SingleGroundMidLeft(9700, 300);
		new GroundFillSmall(9800, 300);
		new SingleGroundMidRight(10000, 300);
		new SingleGroundMidLeft(9700, 400);
		new GroundFillSmall(9800, 400);
		new SingleGroundMidRight(10000, 400);
		new SingleGroundMidLeft(9700, 500);
		new GroundFillSmall(9800, 500);
		new SingleGroundMidRight(10000, 500);
		
		// pillar2
		new GroundTopEdgeSmall(11200, 100);
		new SingleGroundBottomLeft(11200, 200);		
		new SingleGroundBottomRight(11300, 200);	
		new SingleGroundBottomLeft(11200, 300);		
		new SingleGroundBottomRight(11300, 300);	
		new SingleGroundBottomLeft(11200, 400);		
		new SingleGroundBottomRight(11300, 400);	
		new SingleGroundBottomLeft(11200, 500);		
		new SingleGroundBottomRight(11300, 500);	
		new SingleGroundBottomLeft(11200, 600);		
		new SingleGroundBottomRight(11300, 600);	
		
		// pillar3
		new GroundTopEdgeSmall(12000, 0);
		new SingleGroundBottomLeft(12000, 100);		
		new SingleGroundBottomRight(12100, 100);	
		new SingleGroundBottomLeft(12000, 200);		
		new SingleGroundBottomRight(12100, 200);	
		new SingleGroundBottomLeft(12000, 300);		
		new SingleGroundBottomRight(12100, 300);	
		new SingleGroundBottomLeft(12000, 400);		
		new SingleGroundBottomRight(12100, 400);	
		new SingleGroundBottomLeft(12000, 500);		
		new SingleGroundBottomRight(12100, 500);	
			
	}

}
