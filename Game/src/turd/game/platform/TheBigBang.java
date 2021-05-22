package turd.game.platform;

import turd.game.graphics.Texture;
import turd.game.graphics.TextureManager;

public class TheBigBang {

	Texture crane;
	
	public void Bang() {
		
		
		
		crane = TextureManager.get("Object_Crane.png");
		crane.render(255, 0, 255.f);
		
		
		
		
		
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
		
		// objects
		new ObjectCar01(400, 0);
		//new ObjectCrane(400, -800);
		
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
		new SingleGroundMidLeft(4300, 0);
		new SingleGroundMidLeft(4300, -100);
		new SingleGroundMidLeft(4300, -200);
		new SingleGroundTopLeft(4300, -300);

		new SingleGroundMidMid(4400, 0);
		new SingleGroundMidMid(4400, -100);
		new SingleGroundBottomRight(4400, -200);
		new SingleGroundTopRight(4400, -300);

		// step2
		new SingleGroundBottomFarRight(4500, -100);
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

		// top of climb obstical
		new GroundTopSmall(5400, -500);
		new SingleGroundTopRight(5600, -500);
		new HazardSpikes(5700, -500);

		new HazardSpikes(5800, -500);
		new HazardSpikes(5900, -500);
		new HazardSpikes(6000, -500);

		// raised 1
		new SingleGroundMidLeft(6100, -500);
		new SingleGroundMidRight(6200, -500);
		new SingleGroundMidLeft(6100, -600);
		new SingleGroundMidRight(6200, -600);
		new SingleGroundTopLeft(6100, -700);
		new SingleGroundTopRight(6200, -700);
		new HazardSpikes(6300, -500);

		new HazardSpikes(6500, -500);
		new HazardSpikes(6400, -500);
		new GroundFillSmall(6400, -400);
		new HazardSpikes(6500, -500);

		// raised 2
		new SingleGroundTopLeft(6700, -600);
		new SingleGroundMidMid(6800, -500);
		new SingleGroundMidMid(6800, -600);
		new SingleGroundBottomFarLeft(6700, -600);
		new SingleGroundMidMid(6700, -500);
		new SingleGroundBottomLeft(6800, -700);
		new SingleGroundTopLeft(6600, -600);
		new SingleGroundBottomLeftSpikesConnect(6600, -500);
		new SingleGroundTopLeft(6800, -800);
		new SingleGroundTopRight(6900, -800);
		new SingleGroundMidRight(6900, -700);
		new SingleGroundMidRight(6900, -600);
		new SingleGroundMidRight(6900, -500);

		new HazardSpikes(7000, -500);
		new HazardSpikes(7100, -500);
		new HazardSpikes(7200, -500);
		new HazardSpikes(7300, -500);
		new HazardSpikes(7400, -500);
		new HazardSpikes(7500, -500);

		new SingleGroundTopLeft(7600, -500);
		new SingleGroundTopMid(7700, -500);
		new SingleGroundTopMid(7800, -500);

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

		// pillar2 and fall
		new GroundTopEdgeSmall(11200, 100);
		new SingleGroundMidLeft(11200, 200);
		new SingleGroundMidRight(11300, 200);
		new SingleGroundMidLeft(11200, 300);
		new SingleGroundMidRight(11300, 300);
		new SingleGroundMidLeft(11200, 400);
		new SingleGroundMidRight(11300, 400);
		new SingleGroundMidLeft(11200, 500);
		new SingleGroundMidRight(11300, 500);
		new SingleGroundMidLeft(11200, 600);
		new SingleGroundMidRight(11300, 600);

		new SingleGroundMidLeft(11200, 700);
		new SingleGroundMidRight(11300, 700);
		new SingleGroundMidLeft(11200, 800);
		new SingleGroundMidRight(11300, 800);
		new SingleGroundMidLeft(11200, 900);
		new SingleGroundMidRight(11300, 900);

		new SingleGroundMidLeft(11200, 1000);
		new SingleGroundMidLeft(11200, 1100);

		// ledge2 left
		new SingleGroundMidMid(11300, 1000);
		new SingleGroundBottomFarRight(11400, 1000);

		new SingleGroundTopMid(11500, 1000);
		new SingleGroundTopMid(11600, 1000);
		new SingleGroundTopMid(11700, 1000);
		new SingleGroundTopRight(11800, 1000);

		new SingleGroundMidMid(11300, 1100);
		new SingleGroundRoofMidConnectLeft(11400, 1100);
		new SingleGroundRoofMid(11500, 1100);
		new SingleGroundRoofMid(11600, 1100);
		new SingleGroundRoofMid(11700, 1100);
		new SingleGroundRoofRight(11800, 1100);

		new SingleGroundMidLeft(11200, 1200);
		new SingleGroundWallTopRight(11300, 1200);

		// wall continued
		new SingleGroundMidLeft(11200, 1300);
		new SingleGroundMidRight(11300, 1300);
		new SingleGroundMidLeft(11200, 1400);
		new SingleGroundMidRight(11300, 1400);
		new SingleGroundMidLeft(11200, 1500);
		new SingleGroundMidRight(11300, 1500);
		new SingleGroundMidLeft(11200, 1600);
		new SingleGroundMidRight(11300, 1600);
		new SingleGroundMidLeft(11200, 1700);
		new SingleGroundMidRight(11300, 1700);
		new SingleGroundMidLeft(11200, 1800);
		new SingleGroundMidRight(11300, 1800);
		new SingleGroundMidLeft(11200, 1900);
		new SingleGroundMidRight(11300, 1900);
		new SingleGroundMidLeft(11200, 2000);
		new SingleGroundMidRight(11300, 2000);
		new SingleGroundMidLeft(11200, 2100);
		new SingleGroundMidRight(11300, 2100);
		new SingleGroundMidLeft(11200, 2200);
		new SingleGroundMidRight(11300, 2200);
		new SingleGroundMidLeft(11200, 2300);
		new SingleGroundMidRight(11300, 2300);
		new SingleGroundMidLeft(11200, 2400);
		new SingleGroundMidRight(11300, 2400);
		new SingleGroundMidLeft(11200, 2500);
		new SingleGroundMidRight(11300, 2500);
		new SingleGroundMidLeft(11200, 2600);
		new SingleGroundMidRight(11300, 2600);

		new SingleGroundMidLeft(11200, 2700);
		new SingleGroundMidRight(11300, 2700);
		new SingleGroundMidLeft(11200, 2800);
		new SingleGroundMidRight(11300, 2800);
		new SingleGroundMidLeft(11200, 2900);
		new SingleGroundMidRight(11300, 2900);
		new SingleGroundMidLeft(11200, 3000);
		new SingleGroundMidRight(11300, 3000);
		new SingleGroundMidLeft(11200, 3100);
		new SingleGroundMidRight(11300, 3100);
		new SingleGroundMidLeft(11200, 3200);
		new SingleGroundMidRight(11300, 3200);
		new SingleGroundMidLeft(11200, 3300);
		new SingleGroundMidRight(11300, 3300);
		new SingleGroundMidLeft(11200, 3400);
		new SingleGroundMidRight(11300, 3400);
		new SingleGroundMidLeft(11200, 3500);
		new SingleGroundMidRight(11300, 3500);
		new SingleGroundMidLeft(11200, 3600);
		new SingleGroundMidRight(11300, 3600);
		new SingleGroundMidLeft(11200, 3700);
		new SingleGroundMidRight(11300, 3700);

		new SingleGroundBottomFarRight(11400, 3800);
		new SingleGroundBottomFarLeft(11100, 3800);
		new GroundTopLong(11500, 3800);

		new GroundFillLong(11500, 3900);
		new GroundFillLong(11500, 4000);
		new GroundFillLong(11500, 4100);
		new GroundFillLong(11500, 4200);

		new GroundFillLong(10500, 3900);
		new GroundFillLong(10500, 4000);
		new GroundFillLong(10500, 4100);
		new GroundFillLong(10500, 4200);

		new GroundTopMedium(10600, 3800);
		new GroundFillSmall(11200, 3800);

		// pillar3 and fall
		new SingleGroundMidLeft(12000, 0);
		new SingleGroundMidRight(12100, 0);
		new SingleGroundMidLeft(12000, -100);
		new SingleGroundMidRight(12100, -100);
		new SingleGroundMidLeft(12000, -200);
		new SingleGroundMidRight(12100, -200);
		new SingleGroundMidLeft(12000, -300);
		new SingleGroundMidRight(12100, -300);
		new SingleGroundMidLeft(12000, -400);
		new SingleGroundMidRight(12100, -400);
		new SingleGroundMidLeft(12000, -500);
		new SingleGroundMidRight(12100, -500);
		new SingleGroundMidLeft(12000, -600);
		new SingleGroundMidRight(12100, -600);
		new SingleGroundMidLeft(12000, -700);
		new SingleGroundMidRight(12100, -700);
		new SingleGroundMidLeft(12000, -800);
		new SingleGroundMidRight(12100, -800);
		new SingleGroundMidLeft(12000, -900);
		new SingleGroundMidRight(12100, -900);
		new SingleGroundMidLeft(12000, -1000);
		new SingleGroundMidRight(12100, -1000);
		new SingleGroundMidLeft(12000, -1100);
		new SingleGroundMidRight(12100, -1100);
		new SingleGroundMidLeft(12000, -1200);
		new SingleGroundMidRight(12100, -1200);
		new SingleGroundMidLeft(12000, -1300);
		new SingleGroundMidRight(12100, -1300);
		new SingleGroundMidLeft(12000, -1400);
		new SingleGroundMidRight(12100, -1400);
		new GroundTopEdgeSmall(12000, -1500);

		new SingleGroundMidLeft(12000, 100);
		new SingleGroundMidRight(12100, 100);
		new SingleGroundMidLeft(12000, 200);
		new SingleGroundMidRight(12100, 200);
		new SingleGroundMidLeft(12000, 300);
		new SingleGroundMidRight(12100, 300);

		// ledge1 right
		new SingleGroundMidMid(12000, 400);
		new SingleGroundBottomFarLeft(11900, 400);
		new SingleGroundTopLeft(11800, 400);
		new SingleGroundRoofMid(11900, 500);
		new SingleGroundRoofLeft(11800, 500);

		// ground cut out right

		new SingleGroundMidMid(12100, 400);
		new SingleGroundRoofMid(12000, 500);
		new SingleGroundRoofMid(12100, 500);
		new SingleGroundRoofMid(12200, 500);
		new SingleGroundRoofMid(12300, 500);
		new SingleGroundRoofMid(12400, 500);
		new SingleGroundMidMid(12500, 500);
		new SingleGroundTopLeft(12000, 1000);
		new SingleGroundTopMid(12100, 1000);
		new SingleGroundTopMid(12200, 1000);
		new SingleGroundTopMid(12300, 1000);
		new SingleGroundBottomFarLeft(12400, 1000);
		new SingleGroundRoofMidConnectLeft(12200, 1100);
		new SingleGroundRoofMid(12300, 1100);
		new SingleGroundRoofMid(12400, 1100);
		new SingleGroundRoofMid(12500, 1100);
		new SingleGroundRoofRight(12600, 1100);
		new SingleGroundMidRight(12600, 1000);
		new SingleGroundMidRight(12600, 900);
		new SingleGroundMidRight(12600, 800);
		new SingleGroundMidRight(12600, 700);
		new SingleGroundMidRight(12600, 600);
		new SingleGroundMidRight(12600, 500);
		new SingleGroundTopRight(12600, 400);
		new SingleGroundTopMid(12500, 400);
		new SingleGroundTopMid(12400, 400);
		new SingleGroundTopMid(12300, 400);
		new SingleGroundBottomFarRight(12200, 400);
		new SingleGroundMidLeft(12500, 900);
		new SingleGroundMidLeft(12500, 800);
		new SingleGroundMidLeft(12500, 700);
		new SingleGroundWallTopLeft(12500, 600);
		new SingleGroundMidMid(12500, 1000);

		// wall contined
		new SingleGroundMidLeft(12000, 1100);
		new SingleGroundMidMid(12100, 1100);
		new SingleGroundMidLeft(12000, 1200);
		new SingleGroundWallTopRight(12100, 1200);
		new SingleGroundMidLeft(12000, 1300);
		new SingleGroundMidRight(12100, 1300);
		new SingleGroundBottomLeftSpikesConnect(12000, 1400);
		new SingleGroundMidRight(12100, 1400);

		// spikes in fall1 right
		new SingleGroundMidMid(12000, 1500);
		new SingleGroundRoofMidConnectRight(11900, 1500);
		new SingleGroundRoofMid(11800, 1500);
		new SingleGroundRoofLeft(11700, 1500);
		new HazardSpikes(11900, 1400);
		new HazardSpikes(11800, 1400);
		new HazardSpikes(11700, 1400);
		new SingleGroundWallTopLeft(12000, 1600);

		// right wall continued
		new SingleGroundMidRight(12100, 1500);
		new SingleGroundMidRight(12100, 1600);
		new SingleGroundMidLeft(12000, 1700);
		new SingleGroundMidRight(12100, 1700);
		new SingleGroundMidLeft(12000, 1800);
		new SingleGroundMidRight(12100, 1800);
		new SingleGroundMidLeft(12000, 1900);
		new SingleGroundMidRight(12100, 1900);
		new SingleGroundMidLeft(12000, 2000);
		new SingleGroundMidRight(12100, 2000);
		new SingleGroundMidLeft(12000, 2100);
		new SingleGroundMidRight(12100, 2100);
		new SingleGroundMidLeft(12000, 2200);
		new SingleGroundMidRight(12100, 2200);
		new SingleGroundMidLeft(12000, 2300);
		new SingleGroundMidRight(12100, 2300);
		new SingleGroundMidLeft(12000, 2400);
		new SingleGroundMidRight(12100, 2400);
		new SingleGroundMidLeft(12000, 2500);
		new SingleGroundMidRight(12100, 2500);
		new SingleGroundMidLeft(12000, 2600);
		new SingleGroundMidRight(12100, 2600);
		new SingleGroundMidLeft(12000, 2700);
		new SingleGroundMidRight(12100, 2700);
		new SingleGroundMidLeft(12000, 2800);
		new SingleGroundMidRight(12100, 2800);
		new SingleGroundMidLeft(12000, 2900);
		new SingleGroundMidRight(12100, 2900);
		new SingleGroundMidLeft(12000, 3000);
		new SingleGroundMidRight(12100, 3000);
		new SingleGroundMidLeft(12000, 3100);
		new SingleGroundMidRight(12100, 3100);
		new SingleGroundMidLeft(12000, 3200);
		new SingleGroundMidRight(12100, 3200);
		new SingleGroundRoofLeft(12000, 3300);
		new SingleGroundRoofRight(12100, 3300);

	}

}
