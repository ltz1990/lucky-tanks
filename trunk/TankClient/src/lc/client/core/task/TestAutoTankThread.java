package lc.client.core.task;

import lc.client.core.components.TankComp;
import lc.client.core.factory.TankFactory;
import lc.client.environment.ClientConstant;

/**
 * 坦克自动走线程,用来测试
 * @author LUCKY
 *
 */
public class TestAutoTankThread extends Thread {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=0;
		long start=System.currentTimeMillis();
		boolean flag = true;
		while(flag){
			i++;
			TankComp tank=TankFactory.getInstance().getUserTank();
			if(i%100==0){				
				if(tank.getStatu()==ClientConstant.KEY_UP){
					tank.setStatu(ClientConstant.KEY_RIGHT);
				}else if(tank.getStatu()==ClientConstant.KEY_RIGHT){
					tank.setStatu(ClientConstant.KEY_DOWN);
				}else if(tank.getStatu()==ClientConstant.KEY_DOWN){
					tank.setStatu(ClientConstant.KEY_LEFT);
				}else if(tank.getStatu()==ClientConstant.KEY_LEFT){
					tank.setStatu(ClientConstant.KEY_UP);
				}else if(tank.getStatu()==ClientConstant.KEY_STOP){
					tank.setStatu(ClientConstant.KEY_RIGHT);
				}
			}
			tank.shot();
			//tank.run();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println(System.currentTimeMillis()-start);
	}
	
}
