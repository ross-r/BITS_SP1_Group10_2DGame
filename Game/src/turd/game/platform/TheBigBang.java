package turd.game.platform;

public class TheBigBang {

	public void Bang() {

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

		new HazardSpikesGrass(1550, -500);
		new ObjectFridge01(1650, -600);
		new FloatingPlatformMedium(1400, -420);

		// first spikes encounter
		new HazardSpikes(2000, 100);
		new HazardSpikes(2100, 100);

		new SingleGroundBottomRightSpikesConnect(1900, 100);
		new GroundFillSmall(2000, 200);

		new HazardSpikesGrass(2900, -100);
		new HazardSpikesGrass(2800, -100);
		new GroundTopEdgeLong(2200, 0);

		new HazardPit(3200, 150);

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
		new FloatingPlatformSmall(3200, -1100); 

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

		new HazardSpikesGrass(4900, -300);
		new HazardSpikesGrass(5000, -300);

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

		new HazardSpikesGrass(7800, -600);
		new HazardSpikesGrass(7900, -600);
		new HazardSpikesGrass(8000, -600);

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
		new ObjectBarrel02(7800, -1000);
		new ObjectBarrel01(7900, -1000);
		new FloatingPlatformMedium(7600, -900);
		new FloatingPlatformSmall(8600, -800);
		new FloatingPlatformSmall(10000, -700);
		new FloatingPlatformSmall(11000, -800);

		// over-hang platform bellow

		new HazardPit(8700, 250);
		new HazardPit(9300, 250);

		new HazardSpikesGrass(8400, -100);
		new HazardSpikesGrass(8500, -100);
		new HazardSpikesGrass(8600, -100);

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
		new SingleGroundMidMid(11400, 1000);
		new SingleGroundMidMid(11500, 1000);
		new SingleGroundMidMid(11600, 1000);
		new SingleGroundMidMid(11700, 1000);
		new SingleGroundMidRight(11800, 1000);
		new SingleGroundMidMid(11300, 1100);
		new SingleGroundRoofMidConnectLeft(11400, 1100);
		new SingleGroundRoofMid(11500, 1100);
		new SingleGroundRoofMid(11600, 1100);
		new SingleGroundRoofMid(11700, 1100);
		new SingleGroundRoofRight(11800, 1100);

		new SingleGroundMidLeft(11200, 1200);
		new SingleGroundWallTopRight(11300, 1200);

		// wall continued left
		new SingleGroundMidLeft(11200, 1300);
		new SingleGroundMidRight(11300, 1300);
		new SingleGroundMidLeft(11200, 1400);
		new SingleGroundMidRight(11300, 1400);
		new SingleGroundMidLeft(11200, 1500);
		new SingleGroundMidRight(11300, 1500);
		new SingleGroundMidLeft(11200, 1600);
		new SingleGroundMidRight(11300, 1600);

		// spikes 2 on left wall

		new SingleGroundMidMid(11300, 1900);
		new SingleGroundRoofMidConnectLeft(11400, 1900);
		new SingleGroundRoofMid(11500, 1900);
		new SingleGroundRoofMid(11600, 1900);
		new SingleGroundRoofRight(11700, 1900);

		new HazardSpikes(11700, 1800);
		new HazardSpikes(11600, 1800);
		new HazardSpikes(11500, 1800);
		new HazardSpikes(11400, 1800);

		new SingleGroundWallTopRight(11300, 2000);
		new SingleGroundMidLeft(11200, 1800);
		new SingleGroundBottomRightSpikesConnect(11300, 1800);

		// left wall continued
		new SingleGroundMidLeft(11200, 1700);
		new SingleGroundMidRight(11300, 1700);

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

		// floating platforms in wall fall
		new FloatingPlatformSmall(11500, 2300);

		new HazardSpikesGrass(11600, 2650);
		new HazardSpikesGrass(11700, 2650);
		new FloatingPlatformSmall(11600, 2750);
		new FloatingPlatformSmall(11680, 3200);

		// left wall continued
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

		new ObjectFridge02(12100, 820);

		new HazardSpikes(11800, 900);
		new HazardSpikes(11700, 900);
		new HazardSpikes(11600, 900);
		new HazardSpikes(11500, 900);
		new HazardSpikes(11400, 900);

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

		// ground at bottom
		new SingleGroundBottomFarRight(11400, 3800);
		new SingleGroundBottomFarLeft(11100, 3800);

		new GroundTopLong(11500, 3800);
		new GroundFillLong(11500, 3900);
		new GroundFillLong(11500, 4000);
		new GroundFillLong(11500, 4100);
		new GroundFillLong(11500, 4200);

		new HazardSpikesGrass(11500, 3700);
		new HazardSpikesGrass(11600, 3700);
		new HazardSpikesGrass(11700, 3700);
		new HazardSpikesGrass(11800, 3700);
		new HazardSpikesGrass(11900, 3700);
		new HazardSpikesGrass(12000, 3700);
		new HazardSpikesGrass(12100, 3700);

		new ObjectFridge01(12200, 3620);

		new GroundFillLong(10500, 3900);
		new GroundFillLong(10500, 4000);
		new GroundFillLong(10500, 4100);
		new GroundFillLong(10500, 4200);

		new GroundTopMedium(10600, 3800);
		new GroundFillSmall(11200, 3800);

		new GroundTopSmall(12500, 3800);
		new GroundFillSmall(12500, 3900);
		new GroundFillSmall(12500, 4000);
		new GroundFillSmall(12500, 4100);
		new GroundFillSmall(12500, 4200);

		new SingleGroundBottomFarLeft(12700, 3800);
		new SingleGroundMidMid(12800, 3800);
		new GroundFillSmall(12700, 3900);
		new GroundFillSmall(12700, 4000);
		new GroundFillSmall(12700, 4100);
		new GroundFillSmall(12700, 4200);

		new SingleGroundMidMid(12900, 3800);
		new GroundFillLong(13000, 3800);

		new SingleGroundMidMid(12900, 3900);
		new GroundFillLong(13000, 3900);

		new SingleGroundMidMid(12900, 4000);
		new GroundFillLong(13000, 4000);

		new SingleGroundMidMid(12900, 4100);
		new GroundFillLong(13000, 4100);

		new SingleGroundMidMid(12900, 4200);
		new GroundFillLong(13000, 4200);

		new SingleGroundMidMid(12900, 3700);
		new GroundFillLong(13000, 4300);

		new SingleGroundMidLeft(12800, 3700);
		new SingleGroundMidMid(12900, 3600);
		new GroundFillLong(13000, 3600);

		new SingleGroundMidLeft(12800, 3600);
		new SingleGroundMidMid(12900, 3500);
		new GroundFillLong(13000, 3500);

		new SingleGroundMidLeft(12800, 3500);
		new SingleGroundMidMid(12900, 3400);
		new GroundFillLong(13000, 3900);

		new SingleGroundMidLeft(12800, 3400);

		new SingleGroundMidLeft(12800, 3300);

		// boss room
		new SingleGroundMidMid(12900, 3300);
		new SingleGroundMidMid(12900, 3200);
		new SingleGroundBottomFarRight(13000, 3200);

		new ObjectBarrel03(13200, 3100);
		new ObjectBarrel01(13300, 3100);

		new ObjectBarrel02(14400, 3100);
		new ObjectBarrel01(14500, 3100);
		new ObjectBarrel03(14600, 3100);
		new ObjectBarrel01(14550, 3030);

		new GroundTopLong(13100, 3200);
		new GroundTopMedium(14100, 3200);

		new SingleGroundBottomFarLeft(14600, 3200);
		new GroundFillLong(14700, 3200);

		new GroundFillLong(14000, 3300);
		new GroundFillLong(14000, 3400);
		new GroundFillLong(14000, 3500);

		new SingleGroundMidLeft(14700, 3100);
		new SingleGroundMidLeft(14700, 3000);
		new SingleGroundMidLeft(14700, 2900);
		new SingleGroundMidLeft(14700, 2800);
		new SingleGroundMidLeft(14700, 2700);
		new SingleGroundMidLeft(14700, 2600);
		new SingleGroundWallTopLeft(14700, 2500);
		new SingleGroundMidMid(14700, 2400);

		new GroundFillLong(14800, 3100);
		new GroundFillLong(14800, 3000);
		new GroundFillLong(14800, 2900);
		new GroundFillLong(14800, 2800);
		new GroundFillLong(14800, 2700);
		new GroundFillLong(14800, 2600);
		new GroundFillLong(14800, 2500);
		new GroundFillLong(14800, 2400);

		new GroundFillLong(14600, 3300);
		new GroundFillLong(14600, 3400);
		new GroundFillLong(14600, 3500);
		new GroundFillLong(14600, 3600);

		new SingleGroundMidMid(14500, 3500);

		new GroundFillLong(13100, 3300);
		new SingleGroundMidMid(13000, 3300);
		new GroundFillLong(13100, 3400);
		new SingleGroundMidMid(13000, 3400);
		new GroundFillLong(13100, 3700);
		new SingleGroundMidMid(13000, 3700);
		// wall 3 continued
		new SingleGroundMidLeft(12800, 3200);
		new SingleGroundMidRight(12900, 3100);
		new SingleGroundMidLeft(12800, 3100);
		new SingleGroundMidRight(12900, 3000);
		new SingleGroundMidLeft(12800, 3000);
		new SingleGroundMidRight(12900, 2900);
		new SingleGroundMidLeft(12800, 2900);

		new FloatingPlatformSmall(12500, 2900);
		new HazardSpikesGrass(12590, 2800);

		new FloatingPlatformSmall(12220, 3250);
		new HazardSpikesGrass(12240, 3150);

		new SingleGroundMidRight(12900, 2800);
		new SingleGroundMidLeft(12800, 2800);
		new SingleGroundMidRight(12900, 2700);
		new SingleGroundMidLeft(12800, 2700);
		new SingleGroundTopLeft(12800, 2600);
		new SingleGroundTopRight(12900, 2600);

		new SingleGroundRoofLeft(12800, 2400);
		new SingleGroundMidLeft(12800, 2300);
		new SingleGroundMidLeft(12800, 2200);
		new SingleGroundMidLeft(12800, 2100);
		new SingleGroundMidLeft(12800, 2000);
		new SingleGroundMidLeft(12800, 1900);
		new SingleGroundMidLeft(12800, 1800);
		new SingleGroundMidLeft(12800, 1700);
		new SingleGroundMidLeft(12800, 1600);

		new GroundFillLong(12900, 2300);
		new GroundFillLong(12900, 2200);
		new GroundFillLong(12900, 2100);
		new GroundFillLong(12900, 2000);
		new GroundFillLong(12900, 1900);
		new GroundFillLong(12900, 1800);
		new GroundFillLong(12900, 1700);
		new GroundFillLong(12900, 1600);

		new GroundFillLong(13900, 2300);
		new GroundFillLong(13900, 2200);
		new GroundFillLong(13900, 2100);
		new GroundFillLong(13900, 2000);
		new GroundFillLong(13900, 1900);
		new GroundFillLong(13900, 1800);
		new GroundFillLong(13900, 1700);
		new GroundFillLong(13900, 1600);

		new SingleGroundRoofMid(12900, 2400);
		new SingleGroundRoofMid(12900, 2400);
		new SingleGroundRoofMid(13000, 2400);
		new SingleGroundRoofMid(13100, 2400);

		new SingleGroundRoofRight(13200, 2400);

		new HazardSpikesUp(13300, 2400);
		new HazardSpikesUp(13400, 2400);
		new HazardSpikesUp(13500, 2400);
		new HazardSpikesUp(13600, 2400);

		new HazardSpikesUp(13700, 2400);
		new HazardSpikesUp(13800, 2400);
		new HazardSpikesUp(13900, 2400);
		new HazardSpikesUp(14000, 2400);
		new HazardSpikesUp(14100, 2400);
		new HazardSpikesUp(14200, 2400);

		new SingleGroundRoofLeft(14300, 2400);

		new SingleGroundRoofMid(14400, 2400);
		new SingleGroundRoofMid(14500, 2400);

		new SingleGroundRoofMidConnectRight(14600, 2400);
		new GroundFillLong(14900, 2300);
		new GroundFillLong(14900, 2200);
		new GroundFillLong(14900, 2100);

		new FloatingPlatformSmall(13300, 2900);

		new ObjectBarrel04(13800, 2800);
		new FloatingPlatformSmall(13700, 2900);
		new FloatingPlatformSmall(14100, 2900);

	}

}
