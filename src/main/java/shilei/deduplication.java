package shilei;

import java.util.HashMap;
import java.util.HashSet;

public class deduplication
{


	private HashMap<String, HashMap<String, HashSet<String>>> result;

	public deduplication()
	{
		super();
		result = new HashMap<>();

	}

	public HashMap<String, HashMap<String, HashSet<String>>> getResult()
	{
		return result;
	}

	public void setResult(
			HashMap<String, HashMap<String, HashSet<String>>> result)
	{
		this.result = result;
	}

	public void add(String... params)
	{
		if (params.length == 1)
		{
			if (this.result.containsKey(params[0]))
			{
				return;
			}
			else
			{
				this.result.put(params[0], new HashMap<>());
			}
		}
		else if (params.length == 2)
		{
			// 完成第一级
			if (this.result.containsKey(params[0]))
			{
				// 开始第二级
				if (this.result.get(params[0]).containsKey(params[1]))
				{
					return;
				}
				else
				{
					this.result.get(params[0]).put(params[1], new HashSet<>());
				}
			}
			else
			{
				this.result.put(params[0], new HashMap<>());
				this.result.get(params[0]).put(params[1], new HashSet<>());
			}

		}
		else if (params.length == 3)
		{
			// 完成第一级
			if (this.result.containsKey(params[0]))
			{
				// 开始第二级
				if (this.result.get(params[0]).containsKey(params[1]))
				{
					this.result.get(params[0]).get(params[1]).add(params[2]);
				}
				else
				{
					this.result.get(params[0]).put(params[1], new HashSet<>());
					this.result.get(params[0]).get(params[1]).add(params[2]);
				}
			}
			else
			{
				this.result.put(params[0], new HashMap<>());
				this.result.get(params[0]).put(params[1], new HashSet<>());
				this.result.get(params[0]).get(params[1]).add(params[2]);
			}
		}

	}

}
