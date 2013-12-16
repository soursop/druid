/*
 * Druid - a distributed column store.
 * Copyright (C) 2012, 2013  Metamarkets Group Inc.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package io.druid.server.coordinator.rules;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Range;
import io.druid.timeline.DataSegment;
import org.joda.time.DateTime;

import java.util.Map;

/**
 */
public class SizeLoadRule extends LoadRule
{
  private final long low;
  private final long high;
  private final Integer replicants;
  private final String tier;
  private final Range<Long> range;

  @JsonCreator
  public SizeLoadRule(
      @JsonProperty("low") long low,
      @JsonProperty("high") long high,
      @JsonProperty("replicants") Integer replicants,
      @JsonProperty("tier") String tier
  )
  {
    this.low = low;
    this.high = high;
    this.replicants = replicants;
    this.tier = tier;
    this.range = Range.closedOpen(low, high);
  }

  @Override
  public Map<String, Integer> getTieredReplicants()
  {
    return null;
  }

  @Override
  public int getNumReplicants(String tier)
  {
    return 0;
  }

  @Override
  public String getType()
  {
    return "loadBySize";
  }

  @Override
  public boolean appliesTo(DataSegment segment, DateTime referenceTimestamp)
  {
    return range.contains(segment.getSize());
  }
}
